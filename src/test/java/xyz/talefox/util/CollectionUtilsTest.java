package xyz.talefox.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 为 {@link CollectionUtils} 提供单元测试。
 *
 * @author 梁济时
 * @since 2022-02-04
 */
@DisplayName("测试 CollectionUtils 工具类")
class CollectionUtilsTest {
    @Nested
    @DisplayName("测试 empty(Collection) 方法")
    class EmptyTest {
        @Test
        @DisplayName("当集合为null时返回true")
        void should_return_true_when_determine_null_array() {
            boolean ret = CollectionUtils.empty(null);
            assertTrue(ret);
        }

        @Test
        @DisplayName("当集合为空时返回true")
        void should_return_true_when_collection_is_empty() {
            boolean ret = CollectionUtils.empty(Collections.emptyList());
            assertTrue(ret);
        }

        @Test
        @DisplayName("当集合不为null且有值时返回false")
        void should_return_false_when_collection_is_not_empty() {
            boolean ret = CollectionUtils.empty(Arrays.asList(new Object[] {null}));
            assertFalse(ret);
        }
    }

    @Nested
    @DisplayName("测试 notEmpty(Collection) 方法")
    class NotEmptyTest {
        @Test
        @DisplayName("当集合为null时返回false")
        void should_return_false_when_determine_null_array() {
            boolean ret = CollectionUtils.notEmpty(null);
            assertFalse(ret);
        }

        @Test
        @DisplayName("当集合为空时返回false")
        void should_return_false_when_collection_is_empty() {
            boolean ret = CollectionUtils.notEmpty(Collections.emptyList());
            assertFalse(ret);
        }

        @Test
        @DisplayName("当集合不为null且有值时返回true")
        void should_return_true_when_collection_is_not_empty() {
            boolean ret = CollectionUtils.notEmpty(Arrays.asList(new Object[] {null}));
            assertTrue(ret);
        }
    }

    @Nested
    @DisplayName("测试 map(Iterator, Function) 方法")
    class MapTest {
        private Iterator<Integer> iterator;

        @BeforeEach
        @SuppressWarnings("unchecked")
        void setup() {
            this.iterator = (Iterator<Integer>) mock(Iterator.class);
        }

        @Test
        @DisplayName("当iterator为null时抛出异常")
        void should_throws_when_iterator_is_null() {
            assertThrows(IllegalArgumentException.class, () -> CollectionUtils.map(null, Object::toString));
        }

        @Test
        @DisplayName("当mapper为null时抛出异常")
        void should_throws_when_mapper_is_null() {
            assertThrows(IllegalArgumentException.class, () -> CollectionUtils.map(this.iterator, null));
        }

        @Test
        @DisplayName("返回一个iterator，用以访问被映射的值")
        void should_return_iterator_to_visit_mapped_elements() {
            when(this.iterator.hasNext()).thenReturn(true);
            when(this.iterator.next()).thenReturn(1);
            Iterator<String> mapped = CollectionUtils.map(this.iterator, Object::toString);
            assertNotNull(mapped);
            assertTrue(mapped.hasNext());
            assertEquals("1", mapped.next());
        }

        @Test
        @DisplayName("通过返回的iterator删除源中的值")
        void should_remove_item_in_source_map() {
            Iterator<String> mapped = CollectionUtils.map(this.iterator, Object::toString);
            mapped.remove();
            verify(this.iterator, times(1)).remove();
        }
    }

    @Nested
    @DisplayName("测试 unmodifiable(Iterator) 方法")
    class UnmodifiableTest {
        private Iterator<Integer> iterator;

        @BeforeEach
        @SuppressWarnings("unchecked")
        void setup() {
            this.iterator = (Iterator<Integer>) mock(Iterator.class);
        }

        @Test
        @DisplayName("当iterator为null时抛出异常")
        void should_throws_when_iterator_is_null() {
            assertThrows(IllegalArgumentException.class, () -> CollectionUtils.unmodifiable(null));
        }

        @Test
        @DisplayName("可以访问迭代器的 hasNext() 方法")
        void has_next_method_is_allowed() {
            when(this.iterator.hasNext()).thenReturn(true);
            Iterator<Integer> unmodifiable = CollectionUtils.unmodifiable(this.iterator);
            assertNotNull(unmodifiable);
            assertTrue(unmodifiable.hasNext());
            verify(this.iterator, times(1)).hasNext();
        }

        @Test
        @DisplayName("可以访问迭代器的 next() 方法")
        void next_method_is_allowed() {
            when(this.iterator.next()).thenReturn(1);
            Iterator<Integer> unmodifiable = CollectionUtils.unmodifiable(this.iterator);
            assertNotNull(unmodifiable);
            assertEquals(1, unmodifiable.next());
            verify(this.iterator, times(1)).next();
        }

        @Test
        @DisplayName("不可以访问迭代器的 remove() 方法")
        void remove_method_is_not_allowed() {
            Iterator<Integer> unmodifiable = CollectionUtils.unmodifiable(this.iterator);
            assertNotNull(unmodifiable);
            assertThrows(UnsupportedOperationException.class, unmodifiable::remove);
        }
    }
}
