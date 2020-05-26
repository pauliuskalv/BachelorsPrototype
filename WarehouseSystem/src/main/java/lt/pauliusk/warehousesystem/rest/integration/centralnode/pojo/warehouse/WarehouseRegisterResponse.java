package lt.pauliusk.warehousesystem.rest.integration.centralnode.pojo.warehouse;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class WarehouseRegisterResponse {
    private Long id;
    private String name;
    private ZonedDateTime validUntil;
}
