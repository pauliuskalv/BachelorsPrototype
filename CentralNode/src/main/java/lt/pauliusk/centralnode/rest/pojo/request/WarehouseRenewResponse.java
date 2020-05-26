package lt.pauliusk.centralnode.rest.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
public class WarehouseRenewResponse {
    private Long id;
    private String name;
    private ZonedDateTime validUntil;
}
