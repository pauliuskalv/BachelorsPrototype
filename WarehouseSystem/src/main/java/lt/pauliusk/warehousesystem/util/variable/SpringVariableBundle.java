package lt.pauliusk.warehousesystem.util.variable;

import lt.pauliusk.warehousesystem.util.VariableBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class SpringVariableBundle implements VariableBundle, CommandLineRunner {
    private Map<String, Object> variables = new HashMap<>();

    @Value("${server.port}")
    private int port;

    SpringVariableBundle(
            @Autowired Environment environment
    ) {
        variables.put(VariableConst.CENTRAL_NODE_ENDPOINT, environment.getProperty(VariableConst.CENTRAL_NODE_ENDPOINT));
        // variables.put(VariableConst.SERVICE_PORT, environment.getProperty(VariableConst.SERVICE_PORT));
    }

    @Override
    public void setVariable(String name, Object value) {
        variables.put(name, value);
    }

    @Override
    public <T> T getVariable(String name, Class<T> type) {
        Object obj = getVariable(name);

        if (type.isInstance(obj)) {
            return (T) obj;
        } else {
            return null;
        }
    }

    @Override
    public Object getVariable(String name) {
        return variables.get(name);
    }

    @Override
    public void run(String... args) throws Exception {
        // First string is the service name
        setVariable(VariableConst.SERVICE_NAME, args[0]);
    }

    @PostConstruct
    private void postConstruct() {
        setVariable(VariableConst.SERVICE_PORT, port);
    }
}
