package lt.pauliusk.warehousesystem.rest.request;

public interface RequestCompletedListener<T> {
    void requestComplete(T results);
}
