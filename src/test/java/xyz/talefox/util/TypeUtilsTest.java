package xyz.talefox.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("测试 TypeUtils 工具类")
class TypeUtilsTest {
    @Nested
    @DisplayName("测试 empty 方法")
    class EmptyTest {
        @Test
        @DisplayName("多次调用方法，返回相同的实例")
        void should_return_reused_empty_array() {
            Type[] a1 = TypeUtils.emptyArray();
            Type[] a2 = TypeUtils.emptyArray();
            assertNotNull(a1);
            assertEquals(0, a1.length);
            assertSame(a1, a2);
        }
    }

    @Nested
    @DisplayName("测试 ignorePrimitive 方法")
    class IgnorePrimitiveTest {
        @Test
        void should_return_wrapper_of_byte() {
            assertEquals(Byte.class, TypeUtils.ignorePrimitive(byte.class));
        }

        @Test
        void should_return_wrapper_of_short() {
            assertEquals(Short.class, TypeUtils.ignorePrimitive(short.class));
        }

        @Test
        void should_return_wrapper_of_int() {
            assertEquals(Integer.class, TypeUtils.ignorePrimitive(int.class));
        }

        @Test
        void should_return_wrapper_of_long() {
            assertEquals(Long.class, TypeUtils.ignorePrimitive(long.class));
        }

        @Test
        void should_return_wrapper_of_float() {
            assertEquals(Float.class, TypeUtils.ignorePrimitive(float.class));
        }

        @Test
        void should_return_wrapper_of_double() {
            assertEquals(Double.class, TypeUtils.ignorePrimitive(double.class));
        }

        @Test
        void should_return_wrapper_of_char() {
            assertEquals(Character.class, TypeUtils.ignorePrimitive(char.class));
        }

        @Test
        void should_return_wrapper_of_boolean() {
            assertEquals(Boolean.class, TypeUtils.ignorePrimitive(boolean.class));
        }

        @Test
        void should_return_origin_when_not_primitive() {
            assertEquals(String.class, TypeUtils.ignorePrimitive(String.class));
        }

        @Test
        void should_return_null_when_origin_is_null() {
            assertNull(TypeUtils.ignorePrimitive(null));
        }
    }

    @Nested
    @DisplayName("测试 parameterized 方法")
    class ParameterizedTest {
        @Test
        void should_throws_when_raw_is_null() {
            assertThrows(IllegalArgumentException.class,
                    () -> TypeUtils.parameterized(null, null, new Type[] { Integer.class }));
        }

        @Test
        void should_throws_when_number_of_parameters_not_match_arguments() {
            assertThrows(IllegalArgumentException.class,
                    () -> TypeUtils.parameterized(null, Map.class, new Type[] { Integer.class }));
        }

        @Test
        void should_throws_when_arguments_contain_null() {
            assertThrows(IllegalArgumentException.class,
                    () -> TypeUtils.parameterized(null, Map.class, new Type[] { Integer.class, null }));
        }

        @Test
        void happy_path() {
            final Class<?> raw = Map.class;
            final Type[] arguments = new Type[] { Integer.class, String.class };
            ParameterizedType type = TypeUtils.parameterized(null, raw, arguments);
            assertNotNull(type);
            assertNull(type.getOwnerType());
            assertEquals(raw, type.getRawType());
            assertArrayEquals(arguments, type.getActualTypeArguments());
        }

        @Test
        void should_return_string_presentation() {
            ParameterizedType type = TypeUtils.parameterized(null,
                    Map.class, new Type[] { Integer.class, String.class });
            assertEquals("java.util.Map<java.lang.Integer, java.lang.String>", type.toString());
        }
    }

    @Nested
    @DisplayName("测试 wildcard 方法")
    class WildcardTest {
        @Test
        void should_throws_when_both_upper_and_lower_not_empty() {
            assertThrows(IllegalArgumentException.class,
                    () -> TypeUtils.wildcard(new Type[] { Number.class }, new Type[] { Number.class }));
        }

        @Test
        void should_throws_when_upper_bounds_contain_null() {
            assertThrows(IllegalArgumentException.class,
                    () -> TypeUtils.wildcard(new Type[] { null }, TypeUtils.emptyArray()));
        }

        @Test
        void should_throws_when_lower_bounds_contain_null() {
            assertThrows(IllegalArgumentException.class,
                    () -> TypeUtils.wildcard(TypeUtils.emptyArray(), new Type[] { null }));
        }

        @Test
        void should_return_string_presentation_of_wildcard_with_upper_bounds() {
            final Type[] bounds = new Type[] { Number.class, Runnable.class };
            WildcardType wildcard = TypeUtils.wildcard(bounds, TypeUtils.emptyArray());
            assertEquals("? extends java.lang.Number & java.lang.Runnable", wildcard.toString());
        }

        @Test
        void should_return_string_presentation_of_wildcard_with_lower_bounds() {
            final Type[] bounds = new Type[] { Number.class, Runnable.class };
            WildcardType wildcard = TypeUtils.wildcard(TypeUtils.emptyArray(), bounds);
            assertEquals("? super java.lang.Number & java.lang.Runnable", wildcard.toString());
        }
    }

    @Nested
    @DisplayName("测试 genericArray 方法")
    class GenericArrayTest {
        @Test
        void should_throws_when_component_type_is_null() {
            assertThrows(IllegalArgumentException.class,
                    () -> TypeUtils.genericArray(null));
        }

        @Test
        void should_throws_when_component_is_class() {
            assertThrows(IllegalArgumentException.class,
                    () -> TypeUtils.genericArray(Number.class));
        }

        @Test
        void should_throws_when_component_is_wildcard() {
            WildcardType component = mock(WildcardType.class);
            assertThrows(IllegalArgumentException.class,
                    () -> TypeUtils.genericArray(component));
        }

        @Test
        void happy_path() {
            ParameterizedType component = mock(ParameterizedType.class);
            when(component.getTypeName()).thenReturn("component");
            GenericArrayType type = TypeUtils.genericArray(component);
            assertSame(component, type.getGenericComponentType());
            assertEquals("component[]", type.getTypeName());
        }
    }
}
