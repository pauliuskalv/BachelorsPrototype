package lt.pauliusk.centralnode.util.variable;

import lt.pauliusk.centralnode.util.VariableBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class SpringVariableBundle implements VariableBundle, ApplicationRunner {
    private Map<String, Object> variables = new HashMap<>();

    SpringVariableBundle(
            @Autowired Environment environment
    ) {
        // variables.put(VariableConst.CENTRAL_NODE_ENDPOINT, environment.getProperty(VariableConst.CENTRAL_NODE_ENDPOINT));
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
    public void run(ApplicationArguments args) throws Exception {
        for (String argName : args.getOptionNames()) {
            variables.putIfAbsent(argName, String.join(",", args.getOptionValues(argName)));
        }
    }
}
