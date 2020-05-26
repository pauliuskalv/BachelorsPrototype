package lt.pauliusk.centralnode.rest.controller;

import lt.pauliusk.centralnode.rest.pojo.request.WarehouseRegisterResponse;
import lt.pauliusk.centralnode.rest.pojo.request.WarehouseRenewRequest;
import lt.pauliusk.centralnode.rest.pojo.request.WarehouseRenewResponse;
import lt.pauliusk.centralnode.rest.pojo.request.WarehouseRegisterRequest;
import lt.pauliusk.centralnode.session.SessionService;
import lt.pauliusk.centralnode.session.WarehouseInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/warehouse")
public class WarehouseRestController {
  private final SessionService sessionService;

  public WarehouseRestController(
    @Autowired SessionService sessionService
  ) {
    this.sessionService = sessionService;
  }

  @PostMapping("/register")
  public ResponseEntity<WarehouseRegisterResponse> register(
    @RequestBody WarehouseRegisterRequest request,
    HttpServletRequest httpRequest
  ) {
    WarehouseInstance newInstance = sessionService.registerWarehouse(request.getName(), request.getPort(), httpRequest);

    WarehouseRegisterResponse response = new WarehouseRegisterResponse(
      newInstance.getId(),
      newInstance.getName(),
      newInstance.getValidUntil()
    );

    return ResponseEntity.ok(response);
  }

  @PostMapping("/renew")
  public ResponseEntity<WarehouseRenewResponse> renew(
    @RequestBody WarehouseRenewRequest request,
    HttpServletRequest httpRequest
  ) {
    WarehouseInstance warehouseInstance = sessionService.renewInstance(request, httpRequest);

    WarehouseRenewResponse response = new WarehouseRenewResponse(warehouseInstance.getId(), warehouseInstance.getName(), warehouseInstance.getValidUntil());

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity getWarehouseById(
          @PathVariable("id") Long id
  ) {
    WarehouseInstance currInstance = sessionService.getInstance(id);

    if (currInstance == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(currInstance);
  }
}
