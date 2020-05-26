package lt.pauliusk.centralnode;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"io.igia.config.fhir", "lt.pauliusk.centralnode"})
public class ApplicationProperties {
}
