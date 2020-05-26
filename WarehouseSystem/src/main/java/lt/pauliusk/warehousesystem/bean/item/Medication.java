package lt.pauliusk.warehousesystem.bean.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity(name = "medicine")
public class Medication extends WarehouseObject {
    @Column(name = "medicine_code")
    private String medicineCode;
}
