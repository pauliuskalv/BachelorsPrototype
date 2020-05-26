package lt.pauliusk.centralnode.rest.request;

public interface RequestCompletedListener<T> {
    void requestComplete(T results);
}
