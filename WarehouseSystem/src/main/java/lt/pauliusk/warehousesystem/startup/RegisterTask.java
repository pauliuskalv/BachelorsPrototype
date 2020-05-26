package lt.pauliusk.warehousesystem.startup;

import lt.pauliusk.warehousesystem.rest.integration.centralnode.MainServiceReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterTask extends StartupTask {
    private final MainServiceReceiver receiver;

    RegisterTask(
            @Autowired MainServiceReceiver receiver
    ) {
        this.receiver = receiver;
    }

    @Override
    protected void doTask() {
        receiver.register();
        logger.info("Registered successfully!");
    }

    @Override
    protected String getTaskName() {
        return "Register with central node";
    }
}
