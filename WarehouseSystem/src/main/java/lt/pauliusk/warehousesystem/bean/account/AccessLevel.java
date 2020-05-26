package lt.pauliusk.warehousesystem.bean.account;

import lombok.Getter;
import lombok.Setter;
import lt.pauliusk.warehousesystem.bean.DatabaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "access_level")
@Getter
@Setter
public class AccessLevel extends DatabaseEntity {
    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;
}
