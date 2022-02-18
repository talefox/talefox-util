package xyz.talefox.util.support;

import xyz.talefox.util.ArrayUtils;
import xyz.talefox.util.ObjectUtils;
import xyz.talefox.util.TypeUtils;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

/**
 * 为 {@link WildcardType} 提供默认实现。
 *
 * @author 梁济时
 * @since 2021/9/17
 */
public class DefaultWildcardType implements WildcardType {
    private final Type[] upperBounds;
    private final Type[] lowerBounds;

    /**
     * 使用通配符的上限和下限初始化 {@link DefaultWildcardType} 类的新实例。
     *
     * @param upperBounds 表示通配符的上限的 {@link Type}{@code []}。
     * @param lowerBounds 表示通配符的下限的 {@link Type}{@code []}。
     * @throws IllegalArgumentException {@code upperBounds} 和 {@code lowerBounds} 同时不为空，或 {@code upperBounds} 或 {@code lowerBounds} 中存在 {@code null} 元素。
     */
    public DefaultWildcardType(Type[] upperBounds, Type[] lowerBounds) {
        if (ArrayUtils.notEmpty(upperBounds) && ArrayUtils.notEmpty(lowerBounds)) {
            throw new IllegalArgumentException("A wildcard cannot have both upper and lower bounds.");
        } else if (ArrayUtils.containsNull(upperBounds)) {
            throw new IllegalArgumentException("The upper bounds cannot contain null.");
        } else if (ArrayUtils.containsNull(lowerBounds)) {
            throw new IllegalArgumentException("The lower bounds cannot contain null.");
        } else {
            this.upperBounds = ObjectUtils.nullIf(upperBounds, TypeUtils::emptyArray);
            this.lowerBounds = ObjectUtils.nullIf(lowerBounds, TypeUtils::emptyArray);
        }
    }

    @Override
    public Type[] getUpperBounds() {
        return this.upperBounds;
    }

    @Override
    public Type[] getLowerBounds() {
        return this.lowerBounds;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("?");
        appendBounds(builder, "extends", this.getUpperBounds());
        appendBounds(builder, "super", this.getLowerBounds());
        return builder.toString();
    }

    private static void appendBounds(StringBuilder builder, String modifier, Type[] bounds) {
        if (bounds.length > 0) {
            builder.append(' ').append(modifier).append(' ').append(bounds[0].getTypeName());
            for (int i = 1; i < bounds.length; i++) {
                builder.append(' ').append('&').append(' ').append(bounds[i].getTypeName());
            }
        }
    }
}
