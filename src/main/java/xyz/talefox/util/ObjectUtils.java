package xyz.talefox.util;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 为对象提供工具方法。
 *
 * @author 梁济时
 * @since 2020/1/2
 */
public class ObjectUtils {
    /** 隐藏默认构造方法，避免工具类被实例化。 */
    private ObjectUtils() {}

    /**
     * 当指定的值为 {@code null} 时，使用默认值。
     *
     * @param value 表示原始值的 {@link Object}。
     * @param supplier 表示默认值的提供程序的 {@link Supplier}。
     * @param <T> 表示值的类型。
     * @return 若原始值为 {@code null}，则使用默认值；否则使用原始值。
     */
    public static <T> T nullIf(T value, Supplier<T> supplier) {
        if (value == null) {
            if (supplier == null) {
                throw new IllegalArgumentException("The method to supply default value cannot be null.");
            } else {
                return supplier.get();
            }
        } else {
            return value;
        }
    }

    /**
     * 若指定的值不为 {@code null}，则将其按照指定的映射程序进行转换，以得到转换后的值。
     *
     * @param value 表示原始值的 {@link Object}。
     * @param mapper 表示用以对值进行转换的转换程序的 {@link Function}。
     * @param <T> 表示原始值的类型。
     * @param <R> 表示目标值的类型。
     * @return 若原始值为 {@code null}，则为 {@code null}；否则为通过映射程序转换后的值的 {@link Object}。
     * @throws IllegalArgumentException {@code mapper} 为 {@code null}。
     */
    public static <T, R> R mapIf(T value, Function<T, R> mapper) {
        if (value == null) {
            return null;
        } else if (mapper == null) {
            throw new IllegalArgumentException("The mapper to map value to result cannot be null.");
        } else {
            return mapper.apply(value);
        }
    }

    /**
     * 检查两个引用是否指向相同的内容。
     *
     * @param o1 表示待检查的第一个引用的 {@link Object}。
     * @param o2 表示待检查的第二个引用的 {@link Object}。
     * @return 若两个引用指向相同的内容，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean same(Object o1, Object o2) {
        return o1 == o2;
    }

    /**
     * 检查两个引用是否不指向相同的内容。
     *
     * @param o1 表示待检查的第一个引用的 {@link Object}。
     * @param o2 表示待检查的第二个引用的 {@link Object}。
     * @return 若两个引用不指向相同的内容，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean notSame(Object o1, Object o2) {
        return !same(o1, o2);
    }

    /**
     * 组合两个对象。
     * <ul>
     *     <li>若第一个待组合的对象为 {@code null}，则返回第二个对象。</li>
     *     <li>若第二个待组合的对象为 {@code null}，则返回第一个对象。</li>
     *     <li>否则返回使用组合程序组合后的对象。</li>
     * </ul>
     *
     * @param o1 表示待合并的第一个对象的 {@link Object}。
     * @param o2 表示待合并的第二个对象的 {@link Object}。
     * @param combiner 表示当两个对象都不为 {@code null} 时所使用的合并程序的 {@link BiFunction}。
     * @param <T> 表示对象的类型。
     * @return 表示合并后的对象的 {@link Object}。
     * @throws IllegalArgumentException {@code combiner} 为 {@code null}。
     */
    public static <T> T combine(T o1, T o2, BiFunction<T, T, T> combiner) {
        if (o1 == null) {
            return o2;
        } else if (o2 == null) {
            return o1;
        } else if (combiner == null) {
            throw new IllegalArgumentException("The combiner to combine objects cannot be null.");
        } else {
            return combiner.apply(o1, o2);
        }
    }

    /**
     * 关闭资源，并忽略期间产生的异常。
     *
     * @param closeableObject 表示待关闭的对象的 {@link AutoCloseable}。
     */
    public static void close(AutoCloseable closeableObject) {
        if (closeableObject != null) {
            try {
                closeableObject.close();
            } catch (Exception ignored) {
                // Ignore exception while closing resource.
            }
        }
    }

    /**
     * 将对象转为指定的类型。
     *
     * @param value 表示待转换的对象的 {@link Object}。
     * @param <T> 表示预期对象的类型。
     * @return 表示类型转换后的对象的 {@link Object}。
     */
    public static <T> T cast(Object value) {
        @SuppressWarnings("unchecked")
        T actual = (T) value;
        return actual;
    }
}
