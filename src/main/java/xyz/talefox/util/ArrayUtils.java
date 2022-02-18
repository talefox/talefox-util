package xyz.talefox.util;

import java.util.Arrays;
import java.util.Objects;

/**
 * 为数组提供工具方法。
 *
 * @author 梁济时
 * @since 2021/1/7
 */
public class ArrayUtils {
    /** 隐藏默认构造方法，避免工具类被实例化。 */
    private ArrayUtils() {}

    /**
     * 检查指定的数组是一个空数组。
     *
     * @param array 表示待检查的数组的 {@link Object}{@code []}。
     * @param <T> 表示数组中元素的类型。
     * @return 若数组为 {@code null} 或长度为 {@code 0}，则为 {@code true}；否则为 {@code false}。
     */
    public static <T> boolean empty(T[] array) {
        return array == null || array.length < 1;
    }

    /**
     * 检查指定的数组不是一个空数组。
     *
     * @param array 表示待检查的数组的 {@link Object}{@code []}。
     * @param <T> 表示数组中元素的类型。
     * @return 若数组不为 {@code null} 且长度不为 {@code 0}，则为 {@code true}；否则为 {@code false}。
     */
    public static <T> boolean notEmpty(T[] array) {
        return !empty(array);
    }

    /**
     * 检查指定的数组中是否包含为 {@code null} 的元素。
     *
     * @param array 表示待检查的数组的 {@link Object}{@code []}。
     * @param <T> 表示数组中的元素的类型。
     * @return 若数组中包含为 {@code null} 的元素，则为 {@code true}；否则为 {@code false}。
     */
    public static <T> boolean containsNull(T[] array) {
        return array != null && Arrays.stream(array).anyMatch(Objects::isNull);
    }
}
