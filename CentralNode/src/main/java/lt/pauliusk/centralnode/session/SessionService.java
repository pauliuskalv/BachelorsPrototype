package lt.pauliusk.centralnode.session;

import lt.pauliusk.centralnode.rest.integration.notifier.WarehouseNotifierService;
import lt.pauliusk.centralnode.rest.pojo.request.WarehouseRenewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SessionService {
  private final Map<Long, WarehouseInstance> registeredInstances = new ConcurrentHashMap<>();
  private final AtomicLong instanceIdGenerator = new AtomicLong();
  private final int validTimeMinutes = 5;

  private final WarehouseNotifierService warehouseNotifierService;

  public SessionService(
          @Autowired WarehouseNotifierService warehouseNotifierService
  ) {
    this.warehouseNotifierService = warehouseNotifierService;
  }

  public WarehouseInstance getInstance(Long id) {
    WarehouseInstance instance = registeredInstances.get(id);

    if (!isInstanceValid(instance)) {
      // Remove existing instance as its validity period expired
      registeredInstances.remove(instance.getId());
      return null;
    } else {
      return instance;
    }
  }

  public Collection<WarehouseInstance> getRegisteredInstances() {
    return registeredInstances.values();
  }

  public WarehouseInstance registerWarehouse(String name, int port, HttpServletRequest httpRequest) {
    Long id = instanceIdGenerator.incrementAndGet();

    WarehouseInstance newInstance = new WarehouseInstance(
      id,
      name,
      buildEndpointString(httpRequest),
      port,
      ZonedDateTime.now().plusMinutes(validTimeMinutes)
    );

    warehouseNotifierService.notifyNew(newInstance, registeredInstances.values());
    registeredInstances.putIfAbsent(id, newInstance);

    return newInstance;
  }

  public WarehouseInstance renewInstance(WarehouseRenewRequest request, HttpServletRequest httpRequest) {
    WarehouseInstance existingInstance = registeredInstances.get(request.getId());

    if (existingInstance == null) {
      existingInstance = registerWarehouse(request.getName(), request.getPort(), httpRequest);
    } else if (!isInstanceValid(existingInstance)) {
      // Instance is not valid, recreate
      removeWarehouse(existingInstance.getId());
      existingInstance = registerWarehouse(request.getName(), request.getPort(), httpRequest);
    } else {
      // instance is still valid, refresh validity interval
      existingInstance.setValidUntil(ZonedDateTime.now().plusMinutes(validTimeMinutes));
      existingInstance.setEndpoint(buildEndpointString(httpRequest));
      existingInstance.setPort(request.getPort());
    }

    return existingInstance;
  }

  private boolean isInstanceValid(WarehouseInstance instance) {
    return instance.getValidUntil().toInstant().compareTo(ZonedDateTime.now().toInstant()) > 0;
  }

  private void removeWarehouse(Long id) {
    registeredInstances.remove(id);
    warehouseNotifierService.notifyDeleted(id, registeredInstances.values());
  }

  private String buildEndpointString(HttpServletRequest request) {
    return "http://" + request.getRemoteAddr();
  }
}
