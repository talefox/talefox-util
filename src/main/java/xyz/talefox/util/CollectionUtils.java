package xyz.talefox.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

/**
 * 为集合提供工具方法。
 *
 * @author 梁济时
 * @since 2021/9/29
 */
public final class CollectionUtils {
    /** 隐藏默认构造方法，避免工具类被实例化。 */
    private CollectionUtils() {}

    /**
     * 检查指定的集合是否为空。
     * <p>若集合为 {@code null} 或 {@link Collection#isEmpty()} 为 {@code true}，则集合为空。</p>
     *
     * @param collection 表示待检查的集合的 {@link Collection}。
     * @return 若集合为空，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean empty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 检查指定的集合是否不为空。
     * <p>若集合不为 {@code null} 且 {@link Collection#isEmpty()} 为 {@code false}，则集合不为空。</p>
     *
     * @param collection 表示待检查的集合的 {@link Collection}。
     * @return 若集合不为空，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean notEmpty(Collection<?> collection) {
        return !empty(collection);
    }

    /**
     * 将迭代器访问的值进行转换。
     *
     * @param iterator 表示原始迭代器的 {@link Iterator}。
     * @param mapper 表示用以转换值的方法的 {@link Function}。
     * @param <T> 表示迭代器中访问的原始值的类型。
     * @param <R> 表示迭代器访问的新值的类型。
     * @return 表示转换后的迭代器的 {@link Iterator}。
     */
    public static <T, R> Iterator<R> map(Iterator<T> iterator, Function<T, R> mapper) {
        Validation.notNull(iterator, "The iterator to map cannot be null.");
        Validation.notNull(mapper, "The map function to map iterator cannot be null.");
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public R next() {
                return mapper.apply(iterator.next());
            }

            @Override
            public void remove() {
                iterator.remove();
            }
        };
    }

    /**
     * 返回一个装饰器，用以将迭代器设置为只读。
     * <p>{@link Iterator#remove()} 方法将抛出默认异常。</p>
     *
     * @param iterator 表示原始迭代器的 {@link Iterator}。
     * @param <T> 表示迭代器中访问的值的类型。
     * @return 表示只读迭代器的装饰器的 {@link Iterator}。
     */
    public static <T> Iterator<T> unmodifiable(Iterator<T> iterator) {
        Validation.notNull(iterator, "The iterator to make unmodifiable cannot be null.");
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                return iterator.next();
            }
        };
    }
}
