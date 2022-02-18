package xyz.talefox.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * 为反射提供工具方法。
 *
 * @author 梁济时
 * @since 2021/9/23
 */
public final class ReflectionUtils {
    /** 隐藏默认构造方法，避免工具类被实例化。 */
    private ReflectionUtils() {}

    /**
     * 设置字段的值。
     *
     * @param object 表示待设置字段的值的对象的 {@link Object}。
     * @param field 表示待设置值的字段的 {@link Field}。
     * @param value 表示待设置到字段的值的 {@link Object}。
     * @throws IllegalStateException 设置字段的值失败。
     */
    public static void set(Object object, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException(String.format(Locale.ROOT,
                    "Failed to set value for field. [class=%s, field=%s]",
                    field.getDeclaringClass().getName(), field.getName()), ex);
        }
    }

    public static <T> T construct(Constructor<T> constructor, Object... args) {
        constructor.setAccessible(true);
        try {
            return constructor.newInstance(args);
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new IllegalStateException("Failed to construct object.", ex);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("Failed to construct object.", e.getCause());
        }
    }

    public static String signature(Method method) {
        StringBuilder builder = new StringBuilder();
        builder.append(method.getName());
        builder.append('(');
        if (method.getParameterCount() > 0) {
            builder.append(method.getParameters()[0].getParameterizedType().getTypeName());
            for (int i = 1; i < method.getParameterCount(); i++) {
                builder.append(',').append(' ').append(method.getParameters()[i].getParameterizedType().getTypeName());
            }
        }
        builder.append(')');
        return builder.toString();
    }
}
