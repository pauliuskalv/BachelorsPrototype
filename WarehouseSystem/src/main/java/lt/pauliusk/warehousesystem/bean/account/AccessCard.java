package lt.pauliusk.warehousesystem.bean.account;

import lombok.Getter;
import lombok.Setter;
import lt.pauliusk.warehousesystem.bean.DatabaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity(name = "access_card")
@Getter
@Setter
public class AccessCard extends DatabaseEntity {
    @Column(name = "valid_from")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;

    @Column(name = "valid_to")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validTo;
}
