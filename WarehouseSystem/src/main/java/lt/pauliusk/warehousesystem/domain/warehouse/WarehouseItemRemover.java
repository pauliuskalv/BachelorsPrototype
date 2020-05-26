package lt.pauliusk.warehousesystem.domain.warehouse;

import lt.pauliusk.warehousesystem.bean.item.WarehouseObject;

public interface WarehouseItemRemover<T extends WarehouseObject> {
    boolean remove(T item);
}
