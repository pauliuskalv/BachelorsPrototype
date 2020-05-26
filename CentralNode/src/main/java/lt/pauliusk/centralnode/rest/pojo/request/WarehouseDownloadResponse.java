package lt.pauliusk.centralnode.rest.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WarehouseDownloadResponse {
    private Long registeredId;
    private String url;
    private String name;
}
