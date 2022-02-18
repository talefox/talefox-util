package xyz.talefox.util.support;

import xyz.talefox.util.Validation;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

/**
 * 为 {@link GenericArrayType} 提供默认实现。
 *
 * @author 梁济时
 * @since 2021/9/17
 */
public class DefaultGenericArrayType implements GenericArrayType {
    private final Type component;

    /**
     * 使用组件的类型初始化 {@link DefaultGenericArrayType} 类的新实例。
     *
     * @param component 表示数组中组件的类型的 {@link Type}。
     * @throws IllegalArgumentException {@code component} 为 {@code null} 或 {@link Class} 或 {@link WildcardType}。
     */
    public DefaultGenericArrayType(Type component) {
        Validation.notNull(component, "The component type of a generic array cannot be null.");
        if (component instanceof Class) {
            throw new IllegalArgumentException("The component type of a generic array cannot be class.");
        } else if (component instanceof WildcardType) {
            throw new IllegalArgumentException("The component type of a generic array cannot be wildcard.");
        } else {
            this.component = component;
        }
    }

    @Override
    public Type getGenericComponentType() {
        return this.component;
    }

    @Override
    public String toString() {
        return this.component.getTypeName() + "[]";
    }
}
