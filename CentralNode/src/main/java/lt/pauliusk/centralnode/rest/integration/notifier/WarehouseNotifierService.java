package lt.pauliusk.centralnode.rest.integration.notifier;

import com.github.rholder.retry.*;
import lt.pauliusk.centralnode.rest.pojo.request.DeregisterInstanceRequest;
import lt.pauliusk.centralnode.rest.pojo.request.NewWarehouseRequest;
import lt.pauliusk.centralnode.rest.request.rest.RestRequest;
import lt.pauliusk.centralnode.rest.request.rest.RestRequestBuilder;
import lt.pauliusk.centralnode.session.WarehouseInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class WarehouseNotifierService {
    private final Logger logger = Logger.getLogger(WarehouseNotifierService.class.getName());

    private final Retryer notifyRetryer = RetryerBuilder
            .newBuilder()
            .retryIfException()
            .withWaitStrategy(WaitStrategies.fixedWait(5, TimeUnit.SECONDS))
            .withStopStrategy(StopStrategies.stopAfterAttempt(5))
            .build();

    private final String NEW_METHOD = "/warehouse/register";
    private final String DELETED_METHOD = "/warehouse/deregister";

    private final ExecutorService requestExecutor = Executors.newFixedThreadPool(4);
    private final RestRequestBuilder requestBuilder;

    public WarehouseNotifierService(
            @Autowired RestRequestBuilder requestBuilder
    ) {
        this.requestBuilder = requestBuilder;
    }

    public void notifyNew(WarehouseInstance newWarehouse, Collection<WarehouseInstance> currentInstances) {
        for (WarehouseInstance instance : currentInstances) {
            requestExecutor.submit(() -> notifyNewSingle(newWarehouse, instance));
        }
    }

    public void notifyDeleted(Long deletedWarehouseId, Collection<WarehouseInstance> currentInstances) {
        for (WarehouseInstance instance : currentInstances) {
            requestExecutor.submit(() -> notifyDeletedSingle(deletedWarehouseId, instance));
        }
    }

    private void notifyNewSingle(WarehouseInstance newWarehouse, WarehouseInstance instance) {
        try {
            logger.info("Notifying " + instance.getEndpoint() + ":" + instance.getPort());
            notifyRetryer.call(() -> buildNotifyNewRequest(newWarehouse, instance).start(Map.of(), Object.class));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to notify " + instance.getEndpoint());
        }
    }

    private void notifyDeletedSingle(Long deletedWarehouseId, WarehouseInstance instance) {
        try {
            logger.info("Notifying " + instance.getEndpoint() + ":" + instance.getPort());
            buildNotifyDeletedRequest(deletedWarehouseId, instance).start(null, Object.class);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to notify " + instance.getEndpoint());
        }
    }

    private RestRequest buildNotifyNewRequest(WarehouseInstance newWarehouse, WarehouseInstance instance) {
        return requestBuilder.buildPostRequest(
                instance.getEndpoint() + ":" + instance.getPort(),
                NEW_METHOD,
                new NewWarehouseRequest(newWarehouse.getId(), newWarehouse.getName())
        );
    }

    private RestRequest buildNotifyDeletedRequest(Long deletedId, WarehouseInstance instance) {
        return requestBuilder.buildPostRequest(
                instance.getEndpoint() + ":" + instance.getPort(),
                DELETED_METHOD,
                new DeregisterInstanceRequest(deletedId)
        );
    }
}
