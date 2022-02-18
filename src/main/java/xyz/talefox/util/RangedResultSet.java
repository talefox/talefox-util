package xyz.talefox.util;

import xyz.talefox.util.support.DefaultRangedResultSet;

import java.util.List;

/**
 * 为按区间查找提供结果集。
 *
 * @param <T> 表示结果集中的数据的类型。
 * @author 梁济时
 * @since 2022/1/18
 */
public interface RangedResultSet<T> {
    /**
     * 获取区间内的数据记录的集合。
     *
     * @return 表示区间数据集的 {@link List}。
     */
    List<T> results();

    /**
     * 获取区间结果。
     *
     * @return 表示区间结果的 {@link RangeResult}。
     */
    RangeResult range();

    static <T> RangedResultSet<T> create(List<T> results, long offset, int limit, long total) {
        return create(results, RangeResult.create(offset, limit, total));
    }

    static <T> RangedResultSet<T> create(List<T> results, Range range, long total) {
        return create(results, RangeResult.create(range, total));
    }

    /**
     * 使用区间的数据集及区间结果初始化区间结果集默认实现的新实例。
     *
     * @param results 表示数据集的 {@link List}{@code <}{@link T}{@code >}。
     * @param range 表示区间结果的 {@link RangeResult}。
     * @throws IllegalArgumentException {@code results} 或 {@code range} 为 {@code null}。
     */
    static <T> RangedResultSet<T> create(List<T> results, RangeResult range) {
        return new DefaultRangedResultSet<>(results, range);
    }
}
