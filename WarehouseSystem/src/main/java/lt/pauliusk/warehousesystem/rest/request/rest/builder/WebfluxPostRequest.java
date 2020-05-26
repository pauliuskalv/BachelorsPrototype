package lt.pauliusk.warehousesystem.rest.request.rest.builder;

import lt.pauliusk.warehousesystem.rest.request.RequestCompletedListener;
import lt.pauliusk.warehousesystem.rest.request.RequestException;
import lt.pauliusk.warehousesystem.rest.request.rest.RestRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

public class WebfluxPostRequest implements RestRequest {
    private final String url;
    private final String methodTemplate;
    private Object body;

    public WebfluxPostRequest(String url, String methodTemplate) {
        this.url = url;
        this.methodTemplate = methodTemplate;
    }

    public WebfluxPostRequest(String url, String methodTemplate, Object body) {
        this.url = url;
        this.methodTemplate = methodTemplate;
        this.body = body;
    }

    @Override
    public <T> T start(Map<String, Object> args, Class<T> responseType) throws RequestException {
        Mono<T> request = constructRequest(args, responseType);
        return request.block();
    }

    @Override
    public <T> void startAsync(Map<String, Object> args, Class<T> responseType, RequestCompletedListener<T> listener) {
        Mono<T> request = constructRequest(args, responseType);
        request.subscribe(listener::requestComplete);
    }

    private <T> Mono<T> constructRequest(Map<String, Object> args, Class<T> responseType) {
        return WebClient
                .create(url)
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(methodTemplate)
                        .build(args))
                .body(Mono.just(body), ParameterizedTypeReference.forType(body.getClass()))
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> Mono.error(new RequestException()))
                .bodyToMono(responseType);
    }
}
