package lt.pauliusk.warehousesystem.rest.controller;

import lt.pauliusk.warehousesystem.bean.item.Medication;
import lt.pauliusk.warehousesystem.domain.warehouse.accessor.AbstractWarehouseAccessor;
import lt.pauliusk.warehousesystem.rest.integration.centralnode.pojo.medication.MedicationPojo;
import lt.pauliusk.warehousesystem.rest.integration.centralnode.pojo.medication.MedicationSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/medication")
public class MedicationRestController {
    @Autowired
    private AbstractWarehouseAccessor<Medication> medicationAccessor;

    @GetMapping("/all")
    public ResponseEntity<MedicationSearchResponse> getAll() {
        List<MedicationPojo> found = new LinkedList<>();
        for (Medication meds : medicationAccessor.getAll()) {
            found.add(buildPojo(meds));
        }

        return ResponseEntity.ok(new MedicationSearchResponse(found));
    }

    private MedicationPojo buildPojo(Medication meds) {
        MedicationPojo pojo = new MedicationPojo();

        pojo.setId(meds.getId());
        pojo.setReferenceCode(meds.getReferenceCode());
        pojo.setMedicineCode(meds.getMedicineCode());

        return pojo;
    }
}
