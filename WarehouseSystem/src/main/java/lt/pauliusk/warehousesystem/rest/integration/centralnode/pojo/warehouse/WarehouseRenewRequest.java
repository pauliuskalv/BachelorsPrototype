package lt.pauliusk.warehousesystem.rest.integration.centralnode.pojo.warehouse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WarehouseRenewRequest {
    private Long id;
    private String name;
    private int port;
}
