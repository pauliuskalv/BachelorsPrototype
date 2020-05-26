package lt.pauliusk.warehousesystem.rest.integration;

import lt.pauliusk.warehousesystem.bean.item.WarehouseObject;

import java.util.List;

public interface WarehouseReceiver {
    List<Long> receive(Long id);
    List<? extends WarehouseObject> receiveItem(Long warehouseId, Long id);
}
