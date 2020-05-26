package lt.pauliusk.warehousesystem.startup;

import org.springframework.beans.factory.InitializingBean;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class StartupTask implements InitializingBean {
    protected final Logger logger = Logger.getLogger(getClass().getName());

    protected abstract void doTask();
    protected abstract String getTaskName();

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.log(Level.INFO, "Executing task: " + getTaskName());

        doTask();
    }
}
