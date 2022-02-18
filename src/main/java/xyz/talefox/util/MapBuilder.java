package xyz.talefox.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 为 {@link HashMap} 提供构建程序。
 *
 * @param <K> 表示映射中键的类型。
 * @param <V> 表示映射中值的类型。
 * @author 梁济时
 * @since 2020/1/2
 */
public final class MapBuilder<K, V> {
    private final Map<K, V> map;

    /**
     * 初始化 {@link MapBuilder} 类的新实例。
     */
    public MapBuilder() {
        this.map = new LinkedHashMap<>();
    }

    /**
     * 添加一个键值对。
     *
     * @param key 表示键的 {@link Object}。
     * @param value 表示值的 {@link Object}。
     * @return 表示当前构建程序的 {@link MapBuilder}。
     */
    public MapBuilder<K, V> put(K key, V value) {
        this.map.put(key, value);
        return this;
    }

    /**
     * 构建 {@link HashMap} 的新实例。
     *
     * @return 表示新构建的 {@link HashMap} 实例。
     */
    public HashMap<K, V> build() {
        return new HashMap<>(this.map);
    }

    /**
     * 构建映射实例。
     *
     * @param factory 表示用以创建映射的方法的 {@link Supplier}。
     * @param <T> 表示映射的实际类型。
     * @return 表示创建的映射，其中包含已设置的所有条目。
     * @throws IllegalArgumentException {@code factory} 为 {@code null}。
     */
    public <T extends Map<K, V>> T build(Supplier<T> factory) {
        Validation.notNull(factory, "The factory to instantiate map cannot be null.");
        T map = factory.get();
        map.putAll(this.map);
        return map;
    }

    /**
     * 获取一个 {@link HashMap} 构建程序。
     *
     * @param <K> 表示映射中键的类型。
     * @param <V> 表示映射中值的类型。
     * @return 表示用以构建 {@link HashMap} 的构建程序的 {@link MapBuilder}。
     */
    public static <K, V> MapBuilder<K, V> get() {
        return new MapBuilder<>();
    }
}
