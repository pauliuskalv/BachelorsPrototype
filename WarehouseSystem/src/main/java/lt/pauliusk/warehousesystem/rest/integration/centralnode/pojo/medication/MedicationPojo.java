package lt.pauliusk.warehousesystem.rest.integration.centralnode.pojo.medication;

import lombok.Getter;
import lombok.Setter;
import lt.pauliusk.warehousesystem.rest.pojo.BasePojo;

@Getter
@Setter
public class MedicationPojo extends BasePojo {
    private String referenceCode;
    private String medicineCode;
}
