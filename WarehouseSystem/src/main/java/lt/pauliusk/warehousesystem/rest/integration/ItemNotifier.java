package lt.pauliusk.warehousesystem.rest.integration;

import lt.pauliusk.warehousesystem.bean.item.WarehouseObject;

public interface ItemNotifier<T extends WarehouseObject> {
    void itemAdded(T item);
    void itemRemoved(T item);
}
