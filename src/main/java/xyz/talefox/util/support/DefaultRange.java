package xyz.talefox.util.support;

import xyz.talefox.util.Range;

import java.util.Arrays;
import java.util.Locale;

/**
 * 为 {@link Range} 提供默认实现。
 *
 * @author 梁济时
 * @since 2022/1/18
 */
public final class DefaultRange implements Range {
    private final long offset;
    private final int limit;

    /**
     * 使用偏移量和数量限制初始化 {@link DefaultRange} 类的新实例。
     *
     * @param offset 表示区间的偏移量的64位整数。
     * @param limit 表示区间的数量限制的32位整数。
     * @throws IllegalArgumentException {@code offset} 是一个负数或 {@code limit} 不是一个正数。
     */
    public DefaultRange(long offset, int limit) {
        if (offset < 0) {
            throw new IllegalArgumentException(String.format(Locale.ROOT,
                    "The offset of a range cannot be negative. [offset=%d]", offset));
        } else if (limit < 1) {
            throw new IllegalArgumentException(String.format(Locale.ROOT,
                    "The limit of a range must be positive. [limit=%d]", limit));
        } else {
            this.offset = offset;
            this.limit = limit;
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
    public int hashCode() {
        return Arrays.hashCode(new long[] { this.offset(), this.limit() });
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Range) {
            Range another = (Range) obj;
            return another.offset() == this.offset()
                    && another.limit() == this.limit();
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "[offset=%d, limit=%d]", this.offset(), this.limit());
    }
}
