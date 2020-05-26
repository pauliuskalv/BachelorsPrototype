package lt.pauliusk.warehousesystem.bean.log;

import lombok.Getter;
import lombok.Setter;
import lt.pauliusk.warehousesystem.bean.DatabaseEntity;
import lt.pauliusk.warehousesystem.bean.item.WarehouseObject;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "item_history_log")
@Getter
@Setter
public class ItemHistoryLog extends DatabaseEntity {
}
