package lt.pauliusk.warehousesystem.rest.integration.centralnode.pojo.warehouse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WarehouseRegisterRequest {
    private String name;
    private int port;
}
