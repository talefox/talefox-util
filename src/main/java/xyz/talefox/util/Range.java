package xyz.talefox.util;

import xyz.talefox.util.support.DefaultRange;

/**
 * 表示区间定义。
 *
 * @author 梁济时
 * @since 2022/1/18
 */
public interface Range {
    /**
     * 获取区间的偏移量。
     *
     * @return 表示区间的偏移量的64位整数。
     */
    long offset();

    /**
     * 获取区间的数据记录的数量限制。
     *
     * @return 表示数量限制的32位整数。
     */
    int limit();

    /**
     * 使用偏移量和数量限制初始化区间默认实现的新实例。
     *
     * @param offset 表示区间的偏移量的64位整数。
     * @param limit 表示区间的数量限制的32位整数。
     * @throws IllegalArgumentException {@code offset} 是一个负数或 {@code limit} 不是一个正数。
     */
    static Range create(long offset, int limit) {
        return new DefaultRange(offset, limit);
    }
}
