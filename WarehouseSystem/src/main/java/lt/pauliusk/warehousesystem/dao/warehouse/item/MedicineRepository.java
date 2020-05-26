package lt.pauliusk.warehousesystem.dao.warehouse.item;

import lt.pauliusk.warehousesystem.bean.item.Medication;
import lt.pauliusk.warehousesystem.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends BaseDAO<Medication> {
}
