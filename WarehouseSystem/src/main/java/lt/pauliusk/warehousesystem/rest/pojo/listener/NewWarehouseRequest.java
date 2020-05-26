package lt.pauliusk.warehousesystem.rest.pojo.listener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewWarehouseRequest {
    private Long id;
    private String name;
}
