package xyz.talefox.util.support;

import xyz.talefox.util.ArrayUtils;
import xyz.talefox.util.Validation;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Locale;

/**
 * 为 {@link ParameterizedType} 提供默认实现。
 *
 * @author 梁济时
 * @since 2021/9/17
 */
public class DefaultParameterizedType implements ParameterizedType {
    private final Class<?> rawClass;
    private final Type ownerType;
    private final Type[] actualTypeArguments;

    /**
     * 使用定义了泛型类的类型、原始泛型类型及类型参数初始化 {@link DefaultParameterizedType} 类的新实例。
     *
     * @param ownerType 表示定义了参数化类型的类型的 {@link Type}。
     * @param rawClass 表示原始的泛型类型的 {@link Class}。
     * @param actualTypeArguments 表示泛型类型的实际类型参数的 {@link Type}{@code []}。
     * @throws IllegalArgumentException {@code rawClass} 为 {@code null} 或 {@code actualTypeArguments} 是空白数组，或泛型类型所需的参数数量与提供的参数数量不匹配。
     */
    public DefaultParameterizedType(Type ownerType, Class<?> rawClass, Type[] actualTypeArguments) {
        this.ownerType = ownerType;
        this.rawClass = Validation.notNull(rawClass, "The raw class of parameterized type cannot be null.");
        if (this.rawClass.getTypeParameters().length != Validation.notEmpty(actualTypeArguments,
                "The type arguments cannot be null.").length) {
            throw new IllegalArgumentException(String.format(Locale.ROOT,
                    "Type arguments is not matched with required. [required=%d, actual=%d]",
                    this.rawClass.getTypeParameters().length, actualTypeArguments.length));
        } else if (ArrayUtils.containsNull(actualTypeArguments)) {
            throw new IllegalArgumentException("All the type arguments cannot be null.");
        } else {
            this.actualTypeArguments = actualTypeArguments;
        }
    }

    @Override
    public Type getOwnerType() {
        return this.ownerType;
    }

    @Override
    public Type getRawType() {
        return this.rawClass;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return this.actualTypeArguments;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(this.getRawType().getTypeName())
                .append('<').append(this.getActualTypeArguments()[0].getTypeName());
        for (int i = 1; i < this.getActualTypeArguments().length; i++) {
            builder.append(',').append(' ').append(this.getActualTypeArguments()[i].getTypeName());
        }
        return builder.append('>').toString();
    }
}
