package demo.library.funtionalInterface;

public interface ConsumerEx<T> {
    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t) throws Exception;
}
