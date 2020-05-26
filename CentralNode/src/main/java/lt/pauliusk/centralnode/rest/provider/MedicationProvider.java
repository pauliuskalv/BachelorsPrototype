package lt.pauliusk.centralnode.rest.provider;


import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.server.IResourceProvider;
import lt.pauliusk.centralnode.rest.integration.MedicationReceiver;
import lt.pauliusk.centralnode.rest.integration.medication.MedicationPojo;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Medication;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MedicationProvider implements IResourceProvider {
  @Autowired
  private MedicationReceiver receiver;

  @Override
  public Class<? extends IBaseResource> getResourceType() {
    return Medication.class;
  }

  @Search()
  public List<Medication> findAll() {
    List<Medication> found = new LinkedList<>();
    List<MedicationPojo> pojos = receiver.findAll();

    for (MedicationPojo pojo : pojos) {
      Medication medication = new Medication();

      CodeableConcept code = new CodeableConcept();
      code.setText(pojo.getReferenceCode());

      CodeableConcept medicineCode = new CodeableConcept();
      medicineCode.setText(pojo.getMedicineCode());

      medication.setCode(code);
      medication.setForm(medicineCode);

      found.add(medication);
    }

    return found;
  }
}
