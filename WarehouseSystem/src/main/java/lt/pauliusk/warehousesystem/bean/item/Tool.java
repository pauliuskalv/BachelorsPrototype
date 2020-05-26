package lt.pauliusk.warehousesystem.bean.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity(name = "tool")
@Getter
@Setter
public class Tool extends WarehouseObject {

}
