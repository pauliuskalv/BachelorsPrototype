package lt.pauliusk.warehousesystem.bean.account;

import lombok.Getter;
import lombok.Setter;
import lt.pauliusk.warehousesystem.bean.DatabaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity(name = "user")
@Getter
@Setter
public class User extends DatabaseEntity {
    @OneToOne
    private Person person;

    @ManyToMany
    private List<AccessLevel> accesses;

    @OneToOne
    private AccessCard accessCard;
}
