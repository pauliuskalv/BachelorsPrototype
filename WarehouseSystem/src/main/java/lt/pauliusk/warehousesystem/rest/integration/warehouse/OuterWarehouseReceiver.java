package lt.pauliusk.warehousesystem.rest.integration.warehouse;

import lt.pauliusk.warehousesystem.bean.item.WarehouseObject;
import lt.pauliusk.warehousesystem.rest.integration.WarehouseReceiver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OuterWarehouseReceiver implements WarehouseReceiver {

    @Override
    public List<Long> receive(Long id) {
        return null;
    }

    @Override
    public List<? extends WarehouseObject> receiveItem(Long warehouseId, Long id) {
        return null;
    }
}
