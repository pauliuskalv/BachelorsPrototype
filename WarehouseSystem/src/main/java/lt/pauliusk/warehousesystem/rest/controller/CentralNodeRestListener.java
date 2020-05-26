package lt.pauliusk.warehousesystem.rest.controller;

import lt.pauliusk.warehousesystem.rest.integration.warehouse.WarehouseIntegrationService;
import lt.pauliusk.warehousesystem.rest.pojo.listener.DeregisterInstanceRequest;
import lt.pauliusk.warehousesystem.rest.pojo.listener.NewWarehouseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/warehouse")
public class CentralNodeRestListener {
    private final WarehouseIntegrationService warehouseIntegrationService;

    public CentralNodeRestListener(
            @Autowired WarehouseIntegrationService warehouseIntegrationService
    ) {
        this.warehouseIntegrationService = warehouseIntegrationService;
    }

    @PostMapping("/register")
    public ResponseEntity registerNewInstance(
            @RequestBody NewWarehouseRequest request
    ) {
        warehouseIntegrationService.register(request.getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/deregister")
    public ResponseEntity deregisterInstance(
            @RequestBody DeregisterInstanceRequest request
    ) {
        warehouseIntegrationService.deregister(request.getId());

        return ResponseEntity.ok().build();
    }
}
