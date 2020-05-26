package lt.pauliusk.warehousesystem.rest.integration.centralnode;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import lt.pauliusk.warehousesystem.rest.integration.CentralNodeReceiver;
import lt.pauliusk.warehousesystem.rest.integration.centralnode.pojo.warehouse.WarehouseRegisterRequest;
import lt.pauliusk.warehousesystem.rest.integration.centralnode.pojo.warehouse.WarehouseRegisterResponse;
import lt.pauliusk.warehousesystem.rest.integration.centralnode.pojo.warehouse.WarehouseRenewRequest;
import lt.pauliusk.warehousesystem.rest.integration.centralnode.pojo.warehouse.WarehouseRenewResponse;
import lt.pauliusk.warehousesystem.rest.integration.warehouse.Warehouse;
import lt.pauliusk.warehousesystem.rest.integration.warehouse.pojo.WarehouseResponse;
import lt.pauliusk.warehousesystem.rest.request.RequestException;
import lt.pauliusk.warehousesystem.rest.request.rest.RestRequest;
import lt.pauliusk.warehousesystem.rest.request.rest.RestRequestBuilder;
import lt.pauliusk.warehousesystem.util.VariableBundle;
import lt.pauliusk.warehousesystem.util.variable.VariableConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MainServiceReceiver implements CentralNodeReceiver {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private final Retryer<WarehouseRegisterResponse> warehouseRegisterRetryer = RetryerBuilder
            .<WarehouseRegisterResponse>newBuilder()
            .retryIfException()
            .withWaitStrategy(WaitStrategies.fixedWait(5, TimeUnit.SECONDS))
            .build();

    private final Retryer<WarehouseRenewResponse> warehouseRenewRetryer = RetryerBuilder
            .<WarehouseRenewResponse>newBuilder()
            .retryIfException()
            .withWaitStrategy(WaitStrategies.fixedWait(5, TimeUnit.SECONDS))
            .withStopStrategy(StopStrategies.stopAfterAttempt(5))
            .build();

    private final Timer renewTaskTimer = new Timer();

    private final String REGISTER_METHOD = "/warehouse/register";
    private final String RENEW_METHOD = "/warehouse/renew";
    private final String WAREHOUSE_DOWNLOAD_METHOD = "/warehouse/{id}";

    private final RestRequestBuilder requestBuilder;
    private final VariableBundle variableBundle;

    MainServiceReceiver(
            @Autowired RestRequestBuilder requestBuilder,
            @Autowired VariableBundle variableBundle
    ) {
        this.requestBuilder = requestBuilder;
        this.variableBundle = variableBundle;
    }

    @Override
    public boolean register() {
        String serviceName = variableBundle.getVariable(VariableConst.SERVICE_NAME, String.class);

        WarehouseRegisterRequest registerRequest = new WarehouseRegisterRequest(serviceName, variableBundle.getVariable(VariableConst.SERVICE_PORT, Integer.class));
        RestRequest request = buildRegisterRequest(registerRequest);

        try {
            // WarehouseRegisterResponse response = request.start(Map.of(), WarehouseRegisterResponse.class);
            WarehouseRegisterResponse response = warehouseRegisterRetryer.call(() -> request.start(Map.of(), WarehouseRegisterResponse.class));

            variableBundle.setVariable(VariableConst.CENTRAL_NODE_REGISTERED_ID, response.getId());
            variableBundle.setVariable(VariableConst.SERVICE_NAME, response.getName());
            variableBundle.setVariable(VariableConst.CENTRAL_NODE_REGISTERED_ID_VALID_UNTIL, response.getValidUntil());

            setupRenewTask();

            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to register with central node", e);
            return false;
        }
    }

    public void setupRenewTask() {
        ZonedDateTime validUntil = variableBundle.getVariable(VariableConst.CENTRAL_NODE_REGISTERED_ID_VALID_UNTIL, ZonedDateTime.class);

        renewTaskTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                renew();
            }
        }, Date.from(validUntil.minusMinutes(1).toInstant()));
    }

    @Override
    public boolean renew() {
        String serviceName = variableBundle.getVariable(VariableConst.SERVICE_NAME, String.class);
        Long registeredId = variableBundle.getVariable(VariableConst.CENTRAL_NODE_REGISTERED_ID, Long.class);

        WarehouseRenewRequest request = new WarehouseRenewRequest(registeredId, serviceName, variableBundle.getVariable(VariableConst.SERVICE_PORT, Integer.class));
        RestRequest restRequest = buildRenewRequest(request);

        logger.info("Renewing connection with central node");

        try {
            WarehouseRenewResponse response = restRequest.start(Map.of(), WarehouseRenewResponse.class);

            variableBundle.setVariable(VariableConst.CENTRAL_NODE_REGISTERED_ID, response.getId());
            variableBundle.setVariable(VariableConst.SERVICE_NAME, response.getName());
            variableBundle.setVariable(VariableConst.CENTRAL_NODE_REGISTERED_ID_VALID_UNTIL, response.getValidUntil());

            setupRenewTask();

            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to renew connection with central node", e);
            return false;
        }
    }

    public Warehouse downloadWarehouse(Long warehouseId) throws RequestException {
        WarehouseResponse response = buildDownloadRequest().start(
                Map.of(
                        "id", warehouseId
                ), WarehouseResponse.class
        );

        if (response == null) {
            throw new RequestException();
        }

        return new Warehouse(
                response.getRegisteredId(),
                response.getUrl(),
                response.getName()
        );
    }

    private RestRequest buildRegisterRequest(WarehouseRegisterRequest registerRequest) {
        return requestBuilder.buildPostRequest(
                variableBundle.getVariable(VariableConst.CENTRAL_NODE_ENDPOINT, String.class),
                REGISTER_METHOD,
                registerRequest
        );
    }

    private RestRequest buildRenewRequest(WarehouseRenewRequest request) {
        return requestBuilder.buildPostRequest(
                variableBundle.getVariable(VariableConst.CENTRAL_NODE_ENDPOINT, String.class),
                RENEW_METHOD,
                request
        );
    }

    private RestRequest buildDownloadRequest() {
        return requestBuilder.buildGetRequest(
                variableBundle.getVariable(VariableConst.CENTRAL_NODE_ENDPOINT, String.class),
                WAREHOUSE_DOWNLOAD_METHOD
        );
    }
}
