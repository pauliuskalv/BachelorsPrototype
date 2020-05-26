package lt.pauliusk.centralnode.rest.request.rest;

import lt.pauliusk.centralnode.rest.request.RequestCompletedListener;
import lt.pauliusk.centralnode.rest.request.RequestException;

import java.util.Map;

public interface RestRequest {
    <T> T start(Map<String, Object> args, Class<T> responseType) throws RequestException;
    <T> void startAsync(Map<String, Object> args, Class<T> responseType, RequestCompletedListener<T> listener);

    default String getName() {
        return this.getClass().getName();
    }
}
