package lt.pauliusk.warehousesystem.rest.integration.warehouse.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseResponse {
    private Long registeredId;
    private String url;
    private String name;
}
