package xyz.talefox.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("测试 ArrayUtils 工具类")
class ArrayUtilsTest {
    @Nested
    @DisplayName("测试 empty(T[]) 方法")
    class EmptyTest {
        @Test
        @DisplayName("当array为null时返回true")
        void should_return_true_when_array_is_null() {
            assertTrue(ArrayUtils.empty(null));
        }

        @Test
        @DisplayName("当array的长度为0时返回true")
        void should_return_true_when_array_is_empty() {
            assertTrue(ArrayUtils.empty(new Object[0]));
        }

        @Test
        @DisplayName("当array不为null且长度不为0时返回false")
        void should_return_false_when_array_is_not_null() {
            assertFalse(ArrayUtils.empty(new Object[] {null}));
        }
    }

    @Nested
    @DisplayName("测试 notEmpty(T[]) 方法")
    class NotEmptyTest {
        @Test
        @DisplayName("当array为null时返回false")
        void should_return_false_when_array_is_null() {
            assertFalse(ArrayUtils.notEmpty(null));
        }

        @Test
        @DisplayName("当array的长度为0时返回false")
        void should_return_false_when_array_is_empty() {
            assertFalse(ArrayUtils.notEmpty(new Object[0]));
        }

        @Test
        @DisplayName("当array不为null且长度不为0时返回true")
        void should_return_true_when_array_is_not_null() {
            assertTrue(ArrayUtils.notEmpty(new Object[] {null}));
        }
    }

    @Nested
    @DisplayName("测试 contains(T[]) 方法")
    class ContainsNullTest {
        @Test
        @DisplayName("当array为null时返回false")
        void should_return_false_when_array_is_null() {
            assertFalse(ArrayUtils.containsNull(null));
        }

        @Test
        @DisplayName("当array中包含null元素时返回true")
        void should_return_true_when_array_contains_null() {
            assertTrue(ArrayUtils.containsNull(new Object[] {1, 0, null}));
        }

        @Test
        @DisplayName("当array中不包含null元素时返回false")
        void should_return_false_when_array_does_not_contain_null() {
            assertFalse(ArrayUtils.containsNull(new Object[] {0, 1}));
        }
    }
}
