package lt.pauliusk.centralnode.rest.integration;

import lt.pauliusk.centralnode.rest.integration.medication.MedicationPojo;

import java.util.List;

public interface MedicationReceiver {
    public List<MedicationPojo> findAll();
}
