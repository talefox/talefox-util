package xyz.talefox.util;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 为校验提供工具方法。
 *
 * @author 梁济时
 * @since 2021/8/9
 */
public class Validation {
    /** 隐藏默认构造方法，避免工具类被实例化。 */
    private Validation() {}

    private static <T> T validate(T value, Predicate<T> validator, String error) {
        return validate(value, validator, () -> new IllegalArgumentException(error));
    }

    private static <T> T validate(T value, Predicate<T> validator, Supplier<? extends RuntimeException> supplier) {
        if (!validator.test(value)) {
            RuntimeException exception = ObjectUtils.mapIf(supplier, Supplier::get);
            throw ObjectUtils.nullIf(exception, IllegalArgumentException::new);
        } else {
            return value;
        }
    }

    /**
     * 检查指定的值不可为 {@code null}。
     *
     * @param value 表示待检查的值的 {@link Object}。
     * @param error 表示当值为 {@code null} 时，抛出的 {@link IllegalArgumentException} 异常的信息。
     * @param <T> 表示值的类型。
     * @return 当值不为 {@code null} 时的原始值的 {@link Object}。
     */
    public static <T> T notNull(T value, String error) {
        return validate(value, Objects::nonNull, error);
    }

    /**
     * 检查指定的值不可为 {@code null}。
     *
     * @param value 表示待检查的值的 {@link Object}。
     * @param exceptionSupplier 表示当值为 {@code null} 时，用以创建异常的 {@link Supplier}。
     * @param <T> 表示值的类型。
     * @return 当值不为 {@code null} 时的原始值的 {@link Object}。
     */
    public static <T> T notNull(T value, Supplier<? extends RuntimeException> exceptionSupplier) {
        return validate(value, Objects::nonNull, exceptionSupplier);
    }

    /**
     * 检查指定的值不可为空白字符串。
     *
     * @param value 表示待检查的值的 {@link String}。
     * @param error 表示当值为 {@code null} 时，抛出的 {@link IllegalArgumentException} 异常的信息。
     * @return 当值不为 {@code null} 时的原始值的 {@link Object}。
     */
    public static String notBlank(String value, String error) {
        return validate(value, StringUtils::notBlank, error);
    }

    /**
     * 检查指定的值不可为空白字符串。
     *
     * @param value 表示待检查的值的 {@link String}。
     * @param exceptionSupplier 表示当值为 {@code null} 时，用以创建异常的 {@link Supplier}。
     * @return 当值不为 {@code null} 时的原始值的 {@link Object}。
     */
    public static String notBlank(String value, Supplier<? extends RuntimeException> exceptionSupplier) {
        return validate(value, StringUtils::notBlank, exceptionSupplier);
    }

    /**
     * 检查指定的值不可为空白字符串。
     *
     * @param value 表示待检查的值的 {@link String}。
     * @param error 表示当值为 {@code null} 时，抛出的 {@link IllegalArgumentException} 异常的信息。
     * @return 当值不为 {@code null} 时的原始值的 {@link Object}。
     */
    public static String notEmpty(String value, String error) {
        return validate(value, StringUtils::notEmpty, error);
    }

    /**
     * 检查指定的值不可为空白字符串。
     *
     * @param value 表示待检查的值的 {@link String}。
     * @param exceptionSupplier 表示当值为 {@code null} 时，用以创建异常的 {@link Supplier}。
     * @return 当值不为 {@code null} 时的原始值的 {@link Object}。
     */
    public static String notEmpty(String value, Supplier<? extends RuntimeException> exceptionSupplier) {
        return validate(value, StringUtils::notEmpty, exceptionSupplier);
    }

    /**
     * 检查指定值必须为 {@code null} 或空数组。
     *
     * @param value 表示待检查的值的数组。
     * @param error 表示当值为 {@code null} 时，抛出的 {@link IllegalArgumentException} 异常的信息。
     * @param <T> 表示数组中元素的类型。
     * @return 当值不为 {@code null} 或空数组时的原始值的数组。
     */
    public static <T> T[] empty(T[] value, String error) {
        return validate(value, ArrayUtils::empty, error);
    }

    /**
     * 检查指定值必须为 {@code null} 或空数组。
     *
     * @param value 表示待检查的值的数组。
     * @param exceptionSupplier 表示当值为 {@code null} 时，用以创建异常的 {@link Supplier}。
     * @param <T> 表示数组中元素的类型。
     * @return 当值不为 {@code null} 或空数组时的原始值的数组。
     */
    public static <T> T[] empty(T[] value, Supplier<? extends RuntimeException> exceptionSupplier) {
        return validate(value, ArrayUtils::empty, exceptionSupplier);
    }

    /**
     * 检查指定值不可为 {@code null} 或空数组。
     *
     * @param value 表示待检查的值的数组。
     * @param error 表示当值为 {@code null} 时，抛出的 {@link IllegalArgumentException} 异常的信息。
     * @param <T> 表示数组中元素的类型。
     * @return 当值不为 {@code null} 或空数组时的原始值的数组。
     */
    public static <T> T[] notEmpty(T[] value, String error) {
        return validate(value, ArrayUtils::notEmpty, error);
    }

    /**
     * 检查指定值不可为 {@code null} 或空数组。
     *
     * @param value 表示待检查的值的数组。
     * @param exceptionSupplier 表示当值为 {@code null} 时，用以创建异常的 {@link Supplier}。
     * @param <T> 表示数组中元素的类型。
     * @return 当值不为 {@code null} 或空数组时的原始值的数组。
     */
    public static <T> T[] notEmpty(T[] value, Supplier<? extends RuntimeException> exceptionSupplier) {
        return validate(value, ArrayUtils::notEmpty, exceptionSupplier);
    }

    public static int greaterThanOrEquals(int value, int minimum, String error) {
        if (value < minimum) {
            throw new IllegalArgumentException(error);
        } else {
            return value;
        }
    }

    public static String uuid(String value, String error) {
        if (UuidUtils.valid(value)) {
            return value;
        } else {
            throw new IllegalArgumentException(error);
        }
    }

    public static int greaterThan(int value, int minimum, String error) {
        if (value > minimum) {
            return value;
        } else {
            throw new IllegalArgumentException(error);
        }
    }
}
