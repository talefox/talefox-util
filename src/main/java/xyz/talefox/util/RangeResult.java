package xyz.talefox.util;

import xyz.talefox.util.support.DefaultRangeResult;

/**
 * 表示区间的结果信息。
 *
 * @author 梁济时
 * @since 2022/1/18
 */
public interface RangeResult extends Range {
    /**
     * 获取所有符合条件的数据记录的数量。
     *
     * @return 表示数据记录数量的64位整数。
     */
    long total();

    /**
     * 使用有效区间和数据总量初始化区间结果实现的新实例。
     *
     * @param range 表示有效区间的 {@link Range}。
     * @param total 表示数据总量的64位整数。
     * @return 表示新创建的区间结果的 {@link RangeResult}。
     * @throws IllegalArgumentException {@code range} 为 {@code null}。
     */
    static RangeResult create(Range range, long total) {
        if (range == null) {
            throw new IllegalArgumentException("The range to create result cannot be null.");
        } else {
            return create(range.offset(), range.limit(), total);
        }
    }

    /**
     * 使用偏移量、数量限制及数据总量初始化区间结果默认实现的新实例。
     *
     * @param offset 表示偏移量的64位整数。
     * @param limit 表示数量限制的32位整数。
     * @param total 表示数据总量的64位整数。
     * @return 表示新创建的区间结果的 {@link RangeResult}。
     * @throws IllegalArgumentException {@code offset} 是一个负数、{@code limit} 不是一个正数，或 {@code total} 是一个负数。
     */
    static RangeResult create(long offset, int limit, long total) {
        return new DefaultRangeResult(offset, limit, total);
    }
}
