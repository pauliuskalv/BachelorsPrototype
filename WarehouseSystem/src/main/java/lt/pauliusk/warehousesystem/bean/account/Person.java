package lt.pauliusk.warehousesystem.bean.account;

import lombok.Getter;
import lombok.Setter;
import lt.pauliusk.warehousesystem.bean.DatabaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "person")
@Getter
@Setter
public class Person extends DatabaseEntity {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
}
