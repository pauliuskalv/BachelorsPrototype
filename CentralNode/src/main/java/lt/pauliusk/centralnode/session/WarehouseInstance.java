package lt.pauliusk.centralnode.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
public class WarehouseInstance {
  private Long id;
  private String name;
  private String endpoint;
  private int port;
  private ZonedDateTime validUntil;
}
