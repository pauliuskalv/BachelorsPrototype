package lt.pauliusk.centralnode.rest.integration.medication;

import lombok.Getter;
import lombok.Setter;
import lt.pauliusk.centralnode.rest.pojo.BasePojo;

@Getter
@Setter
public class MedicationPojo extends BasePojo {
    private String referenceCode;
    private String medicineCode;
}

