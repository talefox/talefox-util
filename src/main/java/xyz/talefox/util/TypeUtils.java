package xyz.talefox.util;

import xyz.talefox.util.support.DefaultGenericArrayType;
import xyz.talefox.util.support.DefaultParameterizedType;
import xyz.talefox.util.support.DefaultWildcardType;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.*;

/**
 * 为类型提供工具方法。
 *
 * @author 梁济时
 * @since 2021/9/18
 */
public final class TypeUtils {
    private static final Type[] EMPTY_ARRAY = new Type[0];

    private static final Map<Class<?>, Class<?>> WRAPPER_TYPES = MapBuilder.<Class<?>, Class<?>>get()
            .put(byte.class, Byte.class)
            .put(short.class, Short.class)
            .put(int.class, Integer.class)
            .put(long.class, Long.class)
            .put(float.class, Float.class)
            .put(double.class, Double.class)
            .put(char.class, Character.class)
            .put(boolean.class, Boolean.class)
            .build();

    /** 隐藏默认构造方法，避免工具类被实例化。 */
    private TypeUtils() {}

    /**
     * 获取空的类型数组。
     *
     * @return 表示空的类型数组的 {@link Type}{@code []}。
     */
    public static Type[] emptyArray() {
        return EMPTY_ARRAY;
    }

    /**
     * 忽略基本类型。
     *
     * @param clazz 表示待忽略基本类型的数据类型的 {@link Class}。
     * @return 若指定的类型是基本类型，则返回其包装类的 {@link Class}；否则返回原始的对象类型的 {@link Class}。
     */
    public static Class<?> ignorePrimitive(Class<?> clazz) {
        return WRAPPER_TYPES.getOrDefault(clazz, clazz);
    }

    /**
     * 构建一个参数化类型的实例。
     *
     * @param owner 表示所定义在的类型的 {@link Type}。
     * @param raw 表示原始的泛型类型的 {@link Class}。
     * @param arguments 表示类型参数的 {@link Type}{@code []}。
     * @return 表示参数化类型的 {@link ParameterizedType}。
     * @throws IllegalArgumentException {@code rawClass} 为 {@code null} 或 {@code actualTypeArguments} 是空白数组，或泛型类型所需的参数数量与提供的参数数量不匹配。
     */
    public static ParameterizedType parameterized(Type owner, Class<?> raw, Type[] arguments) {
        return new DefaultParameterizedType(owner, raw, arguments);
    }

    /**
     * 构建一个通配符类型的实例。
     *
     * @param upperBounds 表示通配符的上限类型的 {@link Type}{@code []}。
     * @param lowerBounds 表示通配符的下限类型的 {@link Type}{@code []}。
     * @return 表示通配符类型的 {@link WildcardType}。
     * @throws IllegalArgumentException {@code upperBounds} 和 {@code lowerBounds} 同时不为空，或 {@code upperBounds} 或 {@code lowerBounds} 中存在 {@code null} 元素。
     */
    public static WildcardType wildcard(Type[] upperBounds, Type[] lowerBounds) {
        return new DefaultWildcardType(upperBounds, lowerBounds);
    }

    /**
     * 构建一个泛型类型的数组类型。
     *
     * @param component 表示数组中元素的类型的 {@link Type}。
     * @return 表示泛型数组类型的 {@link GenericArrayType}。
     * @throws IllegalArgumentException {@code component} 为 {@code null} 或 {@link Class} 或 {@link WildcardType}。
     */
    public static GenericArrayType genericArray(Type component) {
        return new DefaultGenericArrayType(component);
    }
}
