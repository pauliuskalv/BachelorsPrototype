package lt.pauliusk.warehousesystem.domain.warehouse.accessor.remover.item;

import lt.pauliusk.warehousesystem.bean.item.Medication;
import lt.pauliusk.warehousesystem.domain.warehouse.accessor.remover.AbstractItemRemover;
import org.springframework.stereotype.Service;

@Service
public class MedicationRemover extends AbstractItemRemover<Medication> {
    @Override
    protected Medication doRemove(Medication item) throws Exception {
        // TODO
        return null;
    }
}
