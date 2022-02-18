package xyz.talefox.util.support;

import xyz.talefox.util.RangeResult;

import java.util.Arrays;
import java.util.Locale;

/**
 * 为 {@link RangeResult} 提供默认实现。
 *
 * @author 梁济时
 * @since 2022/1/18
 */
public final class DefaultRangeResult implements RangeResult {
    private final long offset;
    private final int limit;
    private final long total;

    /**
     * 使用偏移量、数量限制及数据总量初始化 {@link DefaultRangeResult} 类的新实例。
     *
     * @param offset 表示偏移量的64位整数。
     * @param limit 表示数量限制的32位整数。
     * @param total 表示数据总量的64位整数。
     * @throws IllegalArgumentException {@code offset} 是一个负数、{@code limit} 不是一个正数，或 {@code total} 是一个负数。
     */
    public DefaultRangeResult(long offset, int limit, long total) {
        if (offset < 0) {
            throw new IllegalArgumentException(String.format(Locale.ROOT,
                    "The offset of a range result cannot be negative. [offset=%d]", offset));
        } else if (limit < 1) {
            throw new IllegalArgumentException(String.format(Locale.ROOT,
                    "The limit of a range result must be positive. [limit=%d]", limit));
        } else if (total < 0) {
            throw new IllegalArgumentException(String.format(Locale.ROOT,
                    "The total of a range result cannot be negative. [total=%d]", total));
        } else {
            this.offset = offset;
            this.limit = limit;
            this.total = total;
        }
    }

    @Override
    public long offset() {
        return this.offset;
    }

    @Override
    public int limit() {
        return this.limit;
    }

    @Override
    public long total() {
        return this.total;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new long[] { this.offset(), this.limit(), this.total() });
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DefaultRangeResult) {
            DefaultRangeResult another = (DefaultRangeResult) obj;
            return another.offset() == this.offset()
                    && another.limit() == this.limit()
                    && another.total() == this.total();
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "[offset=%d, limit=%d, total=%d]",
                this.offset(), this.limit(), this.total());
    }
}
