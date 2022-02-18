package xyz.talefox.util.support;

import xyz.talefox.util.RangeResult;
import xyz.talefox.util.RangedResultSet;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * 为 {@link RangedResultSet} 提供默认实现。
 *
 * @param <T> 表示集合中元素的类型。
 * @author 梁济时
 * @since 2022/1/18
 */
public class DefaultRangedResultSet<T> implements RangedResultSet<T> {
    private final List<T> results;
    private final RangeResult range;

    /**
     * 使用区间的数据集及区间结果初始化 {@link DefaultRangedResultSet} 类的新实例。
     *
     * @param results 表示数据集的 {@link List}{@code <}{@link T}{@code >}。
     * @param range 表示区间结果的 {@link RangeResult}。
     * @throws IllegalArgumentException {@code results} 或 {@code range} 为 {@code null}。
     */
    public DefaultRangedResultSet(List<T> results, RangeResult range) {
        if (results == null) {
            throw new IllegalArgumentException("The results of a ranged result set cannot be null.");
        } else if (range == null) {
            throw new IllegalArgumentException("The range result of a ranged result set cannot be null.");
        } else {
            this.results = results;
            this.range = range;
        }
    }

    @Override
    public List<T> results() {
        return this.results;
    }

    @Override
    public RangeResult range() {
        return this.range;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[] { this.results().hashCode(), this.range().hashCode() });
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DefaultRangedResultSet) {
            @SuppressWarnings("unchecked")
            DefaultRangedResultSet<T> another = (DefaultRangedResultSet<T>) obj;
            return another.results().equals(this.results()) && another.range().equals(this.range());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "[results=%s, range=%s]",
                this.results(), this.range());
    }
}
