package lt.pauliusk.warehousesystem.domain.warehouse;

import lt.pauliusk.warehousesystem.bean.item.WarehouseObject;

import java.util.List;

public interface WarehouseAccessor<T extends WarehouseObject> {
    T getItem(Long id);
    List<T> getItemsByType(Long typeId);
    T removeItem(Long id);
}
