package lt.pauliusk.warehousesystem.bean.definition;

import lombok.Getter;
import lombok.Setter;
import lt.pauliusk.warehousesystem.bean.DatabaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "item_type")
@Getter
@Setter
public class ItemType extends DatabaseEntity {
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent")
    private ItemType parent;

    @OneToMany
    private List<ItemType> subtypes;
}
