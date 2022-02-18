package xyz.talefox.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("测试 RangeResult 工具类")
class RangeResultTest {
    @Test
    @DisplayName("当 offset 为负数时抛出异常")
    void should_throw_exception_when_offset_is_negative() {
        String message = assertThrows(IllegalArgumentException.class,
                () -> RangeResult.create(-1, 1, 100)).getMessage();
        assertEquals("The offset of a range result cannot be negative. [offset=-1]", message);
    }

    @Test
    @DisplayName("当 limit 不为正数时抛出异常")
    void should_throw_exception_when_limit_is_not_positive() {
        Range range = mock(Range.class);
        when(range.offset()).thenReturn(0L);
        when(range.limit()).thenReturn(0);
        String message = assertThrows(IllegalArgumentException.class,
                () -> RangeResult.create(range, 100)).getMessage();
        assertEquals("The limit of a range result must be positive. [limit=0]", message);
    }

    @Test
    @DisplayName("当 range 为 null 时抛出异常")
    void should_throw_exception_when_range_is_null() {
        String message = assertThrows(IllegalArgumentException.class,
                () -> RangeResult.create(null, 100)).getMessage();
        assertEquals("The range to create result cannot be null.", message);
    }

    @Test
    @DisplayName("当两个实例包含相同的数据时，equals(Object) 方法返回 true")
    void should_return_true_when_equals_two_ranges_with_same_data() {
        Range r1 = RangeResult.create(0, 1, 100);
        Range r2 = RangeResult.create(0, 1, 100);
        assertEquals(r1, r2);
    }

    @Test
    @DisplayName("当与实例比较的对象不是 RangeResult 时返回 false")
    void should_return_false_when_equals_to_null() {
        Object range = RangeResult.create(0, 1, 100);
        assertNotEquals(range, null);
    }

    @Test
    @DisplayName("当两个实例包含相同的数据时，hashCode() 返回相同的值")
    void should_return_equals_hash_code_when_ranges_contains_same_data() {
        RangeResult r1 = RangeResult.create(0, 1, 100);
        RangeResult r2 = RangeResult.create(0, 1, 100);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    @DisplayName("数据有效时返回包含正确数据的 RangeResult 实例")
    void should_return_range_contains_correct_data() {
        RangeResult result = RangeResult.create(0, 1, 100);
        assertEquals(0, result.offset());
        assertEquals(1, result.limit());
        assertEquals(100, result.total());
    }

    @Test
    @DisplayName("toString() 方法返回正确的字符串以描述 RangeResult 实例")
    void should_return_string_represents_range() {
        RangeResult range = RangeResult.create(0, 1, 100);
        String text = range.toString();
        assertEquals("[offset=0, limit=1, total=100]", text);
    }
}
