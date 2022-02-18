package xyz.talefox.util;

import java.util.function.Consumer;

/**
 * 为函数方法提供工具方法。
 *
 * @author 梁济时
 * @since 2021/9/22
 */
public final class FunctionUtils {
    @SuppressWarnings("rawtypes")
    private static final Consumer EMPTY_CONSUMER = value -> {};

    /** 隐藏默认构造方法，避免工具类被实例化。 */
    private FunctionUtils() {}

    /**
     * 当指定的值不为 {@code null} 时对值进行消费。
     *
     * @param value 表示待消费的值的 {@link Object}。
     * @param consumer 表示消费者的 {@link Consumer}。
     * @param <T> 表示值的类型。
     * @throws IllegalArgumentException {@code consumer} 为 {@code null}。
     */
    public static <T> void consumeIf(T value, Consumer<T> consumer) {
        Validation.notNull(consumer, "The consumer to consume value cannot be null.");
        if (value != null) {
            consumer.accept(value);
        }
    }

    /**
     * 返回一个空的消费者。
     *
     * @param <T> 表示待消费的值的类型。
     * @return 表示空的消费者的 {@link Consumer}。
     */
    public static <T> Consumer<T> emptyConsumer() {
        @SuppressWarnings("unchecked")
        Consumer<T> consumer = (Consumer<T>) EMPTY_CONSUMER;
        return consumer;
    }

    /**
     * 合并两个消费者。
     *
     * @param consumer 表示原始消费者的 {@link Consumer}。
     * @param another 表示待合并到原始消费者的另一个消费者的 {@link Consumer}。
     * @param <T> 表示待消费的值的类型。
     * @return 表示合并后的消费者的 {@link Consumer}。
     */
    public static <T> Consumer<T> combine(Consumer<T> consumer, Consumer<T> another) {
        if (consumer == null) {
            return another;
        } else if (another == null) {
            return consumer;
        } else {
            return consumer.andThen(another);
        }
    }
}
