package lt.pauliusk.warehousesystem.domain.warehouse.accessor;

import lt.pauliusk.warehousesystem.bean.item.WarehouseObject;
import lt.pauliusk.warehousesystem.dao.BaseDAO;
import lt.pauliusk.warehousesystem.domain.warehouse.WarehouseAccessor;
import lt.pauliusk.warehousesystem.domain.warehouse.WarehouseItemRemover;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractWarehouseAccessor<T extends WarehouseObject> implements WarehouseAccessor<T> {
    @Autowired
    private BaseDAO<T> repository;
    @Autowired
    private WarehouseItemRemover<T> itemRemover;

    public T getItem(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Iterable<T> getAll() {
        return repository.findAll();
    }

    public List<T> getItemsByType(Long typeId) {
        // TODO
        return List.of();
    }

    public T removeItem(Long id) {
        T obj = repository.findById(id).get();

        itemRemover.remove(obj);

        return obj;
    }
}
