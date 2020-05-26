package lt.pauliusk.centralnode.rest.integration.medication;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MedicationSearchResponse {
    private List<MedicationPojo> foundMedications;
}

