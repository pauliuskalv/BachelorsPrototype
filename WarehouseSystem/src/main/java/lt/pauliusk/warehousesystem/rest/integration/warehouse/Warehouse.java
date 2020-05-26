package lt.pauliusk.warehousesystem.rest.integration.warehouse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Warehouse {
    private Long registeredId;
    private String url;
    private String name;
}
