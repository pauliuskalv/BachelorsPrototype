package lt.pauliusk.warehousesystem.rest.integration.warehouse;

import com.github.rholder.retry.*;
import lt.pauliusk.warehousesystem.rest.integration.centralnode.MainServiceReceiver;
import lt.pauliusk.warehousesystem.rest.request.rest.RestRequestBuilder;
import lt.pauliusk.warehousesystem.util.VariableBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class WarehouseIntegrationService {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private final Retryer<Warehouse> warehouseDownloadRetryer = RetryerBuilder
            .<Warehouse>newBuilder()
            .retryIfException()
            .withWaitStrategy(WaitStrategies.fixedWait(5, TimeUnit.SECONDS))
            .withStopStrategy(StopStrategies.stopAfterAttempt(5))
            .build();

    private final ExecutorService pool = Executors.newFixedThreadPool(4);
    private final MainServiceReceiver mainServiceReceiver;
    private final RestRequestBuilder requestBuilder;
    private final VariableBundle variableBundle;

    private final Map<Long, Warehouse> registeredEndpoints = new ConcurrentHashMap<>();

    WarehouseIntegrationService(
            @Autowired MainServiceReceiver mainServiceReceiver,
            @Autowired RestRequestBuilder requestBuilder,
            @Autowired VariableBundle variableBundle
    ) {
        this.mainServiceReceiver = mainServiceReceiver;
        this.requestBuilder = requestBuilder;
        this.variableBundle = variableBundle;
    }

    public void register(Long warehouseId) {
        pool.submit(() -> registerWarehouse(warehouseId));
    }

    public void deregister(Long warehouseId) {
        registeredEndpoints.remove(warehouseId);
    }

    public Collection<Warehouse> getRegisteredEndpoints() {
        return registeredEndpoints.values();
    }

    private void registerWarehouse(Long warehouseId) {
        try {
            Warehouse warehouse = warehouseDownloadRetryer.call(
                    () -> mainServiceReceiver.downloadWarehouse(warehouseId)
            );

            registeredEndpoints.putIfAbsent(warehouse.getRegisteredId(), warehouse);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to download info about warehouse with id " + warehouseId, e);
        }
    }
}
