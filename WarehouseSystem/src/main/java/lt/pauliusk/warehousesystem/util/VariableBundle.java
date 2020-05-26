package lt.pauliusk.warehousesystem.util;

public interface VariableBundle {
    void setVariable(String name, Object value);

    Object getVariable(String name);
    <T> T getVariable(String name, Class<T> type);
}
