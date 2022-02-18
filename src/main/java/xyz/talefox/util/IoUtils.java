package xyz.talefox.util;

import java.io.InputStream;
import java.util.Locale;

/**
 * 为输入输出提供工具方法。
 *
 * @author 梁济时
 * @since 2021/11/29
 */
public class IoUtils {
    /** 隐藏默认构造方法，避免工具类被实例化。 */
    private IoUtils() {}

    /**
     * 从指定对象所在的类型加载程序中查找资源。
     *
     * @param object 表示待查找资源的对象的 {@link Object}。
     * @param resourceKey 表示资源的键的 {@link String}。
     * @return 表示用以读取资源的输入流的 {@link InputStream}。
     * @throws IllegalArgumentException {@code object} 为 {@code null} 或 {@code resourceKey} 是空白字符串。
     * @throws IllegalStateException 资源不存在。
     */
    public static InputStream resource(Object object, String resourceKey) {
        Validation.notNull(object, "The object to lookup resource cannot be null.");
        if (object instanceof Class<?>) {
            return resource((Class<?>) object, resourceKey);
        } else if (object instanceof ClassLoader) {
            return resource((ClassLoader) object, resourceKey);
        } else {
            return resource(object.getClass().getClassLoader(), resourceKey);
        }
    }

    /**
     * 从指定类型所在的类型加载程序中查找资源。
     *
     * @param clazz 表示待查找资源的类型的 {@link Class}。
     * @param resourceKey 表示资源的键的 {@link String}。
     * @return 表示用以读取资源的输入流的 {@link InputStream}。
     * @throws IllegalArgumentException {@code clazz} 为 {@code null} 或 {@code resourceKey} 是空白字符串。
     * @throws IllegalStateException 资源不存在。
     */
    public static InputStream resource(Class<?> clazz, String resourceKey) {
        Validation.notNull(clazz, "The class to lookup resource cannot be null.");
        return resource(clazz.getClassLoader(), resourceKey);
    }

    /**
     * 从指定的类型加载程序中获取指定名称的资源。
     *
     * @param loader 表示拥有资源的类型加载程序的 {@link ClassLoader}。
     * @param resourceKey 表示资源的键的 {@link String}。
     * @return 表示用以读取资源信息的输入流的 {@link InputStream}。
     * @throws IllegalArgumentException {@code loader} 为 {@code null} 或 {@code resourceKey} 是空白字符串。
     * @throws IllegalStateException 资源不存在。
     */
    public static InputStream resource(ClassLoader loader, String resourceKey) {
        Validation.notNull(loader, "The class loader to lookup resource cannot be null.");
        Validation.notNull(resourceKey, "The key of resource to lookup cannot be null.");
        if (resourceKey.startsWith("/")) {
            resourceKey = resourceKey.substring(1);
        }
        InputStream in = loader.getResourceAsStream(resourceKey);
        if (in == null) {
            throw new IllegalStateException(String.format(Locale.ROOT,
                    "Resource not found in class loader. [key=%s]", resourceKey));
        } else {
            return in;
        }
    }
}
