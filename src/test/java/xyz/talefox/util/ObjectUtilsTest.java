package xyz.talefox.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("测试 ObjectUtils 工具类")
class ObjectUtilsTest {
    @Nested
    @DisplayName("测试 nullIf(T, T) 方法")
    class NullIfTest {
        @Test
        @DisplayName("当当前值不为null时返回当前值")
        void should_return_value_when_not_null() {
            Integer value = ObjectUtils.nullIf(1, null);
            assertEquals(1, value);
        }

        @Test
        @DisplayName("当当前值为null，且默认值的创建方法为null时抛出异常")
        void should_throws_when_value_is_null_and_supplier_is_null() {
            assertThrows(IllegalArgumentException.class, () -> ObjectUtils.nullIf(null, null));
        }

        @Test
        @DisplayName("当当前值为null，返回通过默认值的创建方法创建的值")
        void should_return_default_value_when_value_is_null() {
            Integer value = ObjectUtils.nullIf(null, () -> 1);
            assertEquals(1, value);
        }
    }

    @Nested
    @DisplayName("测试 mapIf(T, Function) 方法")
    class MapIfTest {
        @Test
        @DisplayName("当原始值为null时返回null，且未指定mapper方法")
        @SuppressWarnings({"unchecked", "rawtypes"})
        void should_return_null_when_map_null() {
            Function mapper = mock(Function.class);
            assertNull(ObjectUtils.mapIf(null, mapper));
            verify(mapper, times(0)).apply(any());
        }

        @Test
        @DisplayName("当原始值不为null，但mapper方法为null时抛出异常")
        void should_throws_when_value_not_null_but_mapper_is_null() {
            assertThrows(IllegalArgumentException.class, () -> ObjectUtils.mapIf(1, null));
        }

        @Test
        @DisplayName("当原始值不为null，且mapper方法不为null时，返回映射后的值")
        void should_return_mapped_value() {
            String result = ObjectUtils.mapIf(15, Integer::toHexString);
            assertEquals("f", result);
        }
    }

    @Nested
    @DisplayName("测试 same(Object, Object) 方法")
    class SameTest {
        @Test
        @DisplayName("当两个引用指向相同的对象时返回true")
        void should_return_true_when_same_references() {
            Object obj = new byte[0];
            boolean ret = ObjectUtils.same(obj, obj);
            assertTrue(ret);
        }

        @Test
        @DisplayName("当两个引用的值相同，但是指向不同的引用时返回false")
        void should_return_false_when_not_same_but_equals() {
            StringBuilder builder = new StringBuilder().append(1);
            String s1 = builder.toString();
            String s2 = builder.toString();
            boolean ret = ObjectUtils.same(s1, s2);
            assertFalse(ret);
        }

        @Test
        @DisplayName("当两个值均为null时返回true")
        void should_return_true_when_both_null() {
            assertTrue(ObjectUtils.same(null, null));
        }
    }

    @Nested
    @DisplayName("测试 notSame(Object, Object) 方法")
    class NotSameTest {
        @Test
        @DisplayName("当两个引用指向相同的对象时返回false")
        void should_return_false_when_same_references() {
            Object obj = new byte[0];
            boolean ret = ObjectUtils.notSame(obj, obj);
            assertFalse(ret);
        }

        @Test
        @DisplayName("当两个引用的值相同，但是指向不同的引用时返回true")
        void should_return_true_when_not_same_but_equals() {
            StringBuilder builder = new StringBuilder().append(1);
            String s1 = builder.toString();
            String s2 = builder.toString();
            boolean ret = ObjectUtils.notSame(s1, s2);
            assertTrue(ret);
        }

        @Test
        @DisplayName("当两个值均为null时返回false")
        void should_return_true_when_both_null() {
            assertFalse(ObjectUtils.notSame(null, null));
        }
    }

    @Nested
    @DisplayName("测试 combine(T, T, BiFunction) 方法")
    class CombineTest {
        @Test
        @DisplayName("当待组合的两个值均为null时返回null")
        void should_return_null_when_combine_nulls() {
            assertNull(ObjectUtils.combine(null, null, null));
        }

        @Test
        @DisplayName("当待组合的第一个值为null，第二个值不为null时，返回第二个值")
        void should_return_first_when_second_is_null() {
            Object obj = new byte[0];
            Object result = ObjectUtils.combine(obj, null, null);
            assertSame(obj, result);
        }

        @Test
        @DisplayName("当待组合的第一个值不为null，第二个值为null时，返回第一个值")
        void should_return_second_when_first_is_null() {
            Object obj = new byte[0];
            Object result = ObjectUtils.combine(null, obj, null);
            assertSame(obj, result);
        }

        @Test
        @DisplayName("当待组合的两个值均不为null，但组合方法为null时抛出异常")
        void should_throws_when_values_not_null_and_combiner_is_null() {
            assertThrows(IllegalArgumentException.class, () -> ObjectUtils.combine(new byte[0], new byte[0], null));
        }

        @Test
        @DisplayName("当待组合的值和组合方法都不为null时，返回组合后的值")
        void should_return_combined_when_values_not_null() {
            Object first = new byte[0];
            Object second = new byte[0];
            Object combined = new byte[0];
            Object result = ObjectUtils.combine(first, second, (o1, o2) -> combined);
            assertSame(combined, result);
        }
    }

    @Nested
    @DisplayName("测试 close(AutoCloseable) 方法")
    class CloseTest {
        @Test
        @DisplayName("当待关闭的对象为null时不会抛出异常")
        void should_not_throws_when_object_is_null() {
            assertDoesNotThrow(() -> ObjectUtils.close(null));
        }

        @Test
        @DisplayName("当待关闭的对象不为null时，调用close方法")
        void should_close_object() throws Exception {
            AutoCloseable obj = mock(AutoCloseable.class);
            ObjectUtils.close(obj);
            verify(obj, times(1)).close();
        }

        @Test
        @DisplayName("当待关闭的对象在执行close方法抛出异常时，异常被忽略")
        void should_do_nothing_when_close_throws() throws Exception {
            AutoCloseable obj = mock(AutoCloseable.class);
            doThrow(new IllegalStateException()).when(obj).close();
            assertDoesNotThrow(() -> ObjectUtils.close(obj));
        }
    }

    @Nested
    @DisplayName("测试 cast(Object) 方法")
    class CastTest {
        @Test
        @DisplayName("当值是预期类型的实例时，返回预期类型下的值")
        void should_return_value_with_expected_type() {
            Integer value = ObjectUtils.cast(1);
            assertEquals(1, value);
        }

        @Test
        @DisplayName("当值不是预期类型时，抛出异常")
        void should_throw_exception_when_value_without_expected_type() {
            assertThrows(ClassCastException.class, () -> {
                Integer result = ObjectUtils.cast("hello");
                fail("Cast successfully but fault required: " + result);
            });
        }
    }
}
