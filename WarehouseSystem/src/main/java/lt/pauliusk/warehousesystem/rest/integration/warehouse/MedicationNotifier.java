package lt.pauliusk.warehousesystem.rest.integration.warehouse;

import lt.pauliusk.warehousesystem.bean.item.Medication;
import lt.pauliusk.warehousesystem.rest.integration.ItemNotifier;
import org.springframework.stereotype.Service;

@Service
public class MedicationNotifier implements ItemNotifier<Medication> {
    @Override
    public void itemAdded(Medication item) {

    }

    @Override
    public void itemRemoved(Medication item) {

    }
}
