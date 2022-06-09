package lambda;

@FunctionalInterface
public interface Callback<T> {

    public T Call();
}
