package xyz.talefox.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("测试 Range 工具类")
class RangeTest {
    @Test
    @DisplayName("当 offset 为负数时抛出异常")
    void should_throw_exception_when_offset_is_negative() {
        String message = assertThrows(IllegalArgumentException.class,
                () -> Range.create(-1, 1)).getMessage();
        assertEquals("The offset of a range cannot be negative. [offset=-1]", message);
    }

    @Test
    @DisplayName("当 limit 不为正数时抛出异常")
    void should_throw_exception_when_limit_is_not_positive() {
        String message = assertThrows(IllegalArgumentException.class,
                () -> Range.create(0, 0)).getMessage();
        assertEquals("The limit of a range must be positive. [limit=0]", message);
    }

    @Test
    @DisplayName("当两个实例包含相同的数据时，equals(Object) 方法返回 true")
    void should_return_true_when_equals_two_ranges_with_same_data() {
        Range r1 = Range.create(0, 1);
        Range r2 = Range.create(0, 1);
        assertEquals(r1, r2);
    }

    @Test
    @DisplayName("当与实例比较的对象不是 Range 时返回 false")
    void should_return_false_when_equals_to_null() {
        Object range = Range.create(1, 100);
        assertNotEquals(range, null);
    }

    @Test
    @DisplayName("当两个实例包含相同的数据时，hashCode() 返回相同的值")
    void should_return_equals_hash_code_when_ranges_contains_same_data() {
        Range r1 = Range.create(0, 1);
        Range r2 = Range.create(0, 1);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    @DisplayName("数据有效时返回包含正确数据的 Range 实例")
    void should_return_range_contains_correct_data() {
        Range range = Range.create(1, 100);
        assertEquals(1, range.offset());
        assertEquals(100, range.limit());
    }

    @Test
    @DisplayName("toString() 方法返回正确的字符串以描述 Range 实例")
    void should_return_string_represents_range() {
        Range range = Range.create(1, 100);
        String text = range.toString();
        assertEquals("[offset=1, limit=100]", text);
    }
}
