package lt.pauliusk.warehousesystem.domain.warehouse.accessor.remover;

import lt.pauliusk.warehousesystem.bean.item.WarehouseObject;
import lt.pauliusk.warehousesystem.dao.BaseDAO;
import lt.pauliusk.warehousesystem.domain.warehouse.WarehouseItemRemover;
import lt.pauliusk.warehousesystem.rest.integration.ItemNotifier;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractItemRemover<T extends WarehouseObject> implements WarehouseItemRemover<T> {
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    protected BaseDAO<T> repository;

    @Autowired
    private ItemNotifier<T> notifier;

    protected abstract T doRemove(T item) throws Exception;

    @Override
    public boolean remove(T item) {
        try {
            doRemove(item);

            notifier.itemRemoved(item);
            item.setId(null);

            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred while removing item from warehouse", e);
            return false;
        }
    }
}
