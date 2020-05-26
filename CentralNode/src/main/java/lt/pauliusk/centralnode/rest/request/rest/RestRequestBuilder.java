package lt.pauliusk.centralnode.rest.request.rest;

public interface RestRequestBuilder {
    RestRequest buildGetRequest(String url, String methodTemplate);

    RestRequest buildPostRequest(String url, String methodTemplate);
    RestRequest buildPostRequest(String url, String methodTemplate, Object body);
}
