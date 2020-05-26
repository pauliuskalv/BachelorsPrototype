package lt.pauliusk.centralnode.rest.request.rest.builder;

import lt.pauliusk.centralnode.rest.request.rest.RestRequest;
import lt.pauliusk.centralnode.rest.request.rest.RestRequestBuilder;
import org.springframework.stereotype.Service;

@Service
public class WebfluxRequestBuilder implements RestRequestBuilder {
    @Override
    public RestRequest buildGetRequest(String url, String methodTemplate) {
        return new WebfluxGetRequest(url, methodTemplate);
    }

    @Override
    public RestRequest buildPostRequest(String url, String methodTemplate) {
        return new WebfluxPostRequest(url, methodTemplate);
    }

    @Override
    public RestRequest buildPostRequest(String url, String methodTemplate, Object body) {
        return new WebfluxPostRequest(url, methodTemplate, body);
    }
}
