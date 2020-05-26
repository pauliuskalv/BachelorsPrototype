package lt.pauliusk.warehousesystem.rest.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemTypePojo extends BasePojo {
    private String name;
    private Long parentId;
}
