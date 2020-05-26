package lt.pauliusk.centralnode.rest.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewWarehouseRequest {
    private Long id;
    private String name;
}
