package lt.pauliusk.warehousesystem.rest.integration.centralnode.pojo.medication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MedicationSearchResponse {
    private List<MedicationPojo> foundMedications;
}
