package lt.pauliusk.centralnode.rest.integration.medication;

import lt.pauliusk.centralnode.rest.integration.MedicationReceiver;
import lt.pauliusk.centralnode.rest.request.RequestException;
import lt.pauliusk.centralnode.rest.request.rest.RestRequest;
import lt.pauliusk.centralnode.rest.request.rest.RestRequestBuilder;
import lt.pauliusk.centralnode.session.SessionService;
import lt.pauliusk.centralnode.session.WarehouseInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MedicationReceiverImpl implements MedicationReceiver {
    private Logger logger = Logger.getLogger(MedicationReceiverImpl.class.getName());

    private final String SEARCH_METHOD = "/api/medication/all";

    private final RestRequestBuilder restRequestBuilder;
    private final SessionService sessionService;

    public MedicationReceiverImpl(
            @Autowired RestRequestBuilder restRequestBuilder,
            @Autowired SessionService sessionService
            ) {
        this.restRequestBuilder = restRequestBuilder;
        this.sessionService = sessionService;
    }

    @Override
    public List<MedicationPojo> findAll() {
        List<MedicationPojo> found = new LinkedList<>();
        for (WarehouseInstance instance : sessionService.getRegisteredInstances()) {
            try {
                found.addAll(buildRequest(instance).start(Map.of(), MedicationSearchResponse.class).getFoundMedications());
            } catch (RequestException e) {
                logger.log(Level.SEVERE, "Failed to get medication list from " + instance.getEndpoint());
            }
        }

        return found;
    }

    private RestRequest buildRequest(WarehouseInstance instance) {
        return restRequestBuilder.buildGetRequest(
                instance.getEndpoint() + ":" + instance.getPort(),
                SEARCH_METHOD
        );
    }
}
