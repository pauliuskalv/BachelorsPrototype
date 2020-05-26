package lt.pauliusk.warehousesystem.dao;

import lt.pauliusk.warehousesystem.bean.DatabaseEntity;
import org.springframework.data.repository.CrudRepository;

public interface BaseDAO<T extends DatabaseEntity> extends CrudRepository<T, Long> {
}
