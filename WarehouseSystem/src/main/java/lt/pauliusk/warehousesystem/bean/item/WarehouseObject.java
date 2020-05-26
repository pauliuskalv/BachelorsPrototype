package lt.pauliusk.warehousesystem.bean.item;

import lombok.Getter;
import lombok.Setter;
import lt.pauliusk.warehousesystem.bean.DatabaseEntity;
import lt.pauliusk.warehousesystem.bean.definition.ItemType;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class WarehouseObject extends DatabaseEntity {
    @Column(name = "reference_code")
    private String referenceCode;

    @ManyToOne
    @JoinColumn(name = "type")
    private ItemType type;

    @Column(name = "date_added")
    private Date dateAdded;
}
