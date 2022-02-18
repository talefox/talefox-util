package xyz.talefox.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("测试 UuidUtils 工具类")
class UuidUtilsTest {
    @Nested
    @DisplayName("测试 valid 方法")
    class ValidTest {
        @Test
        @DisplayName("当字符串为 null 时，返回 false")
        void should_return_false_when_string_is_null() {
            boolean ret = UuidUtils.valid(null);
            assertFalse(ret);
        }

        @Test
        @DisplayName("当字符串长度不为36时，返回 false")
        void should_return_false_when_string_length_is_not_36() {
            boolean ret = UuidUtils.valid("00000000-00000000-0000-000000000000");
            assertFalse(ret);
        }

        @Test
        @DisplayName("当字符串包含非16进制字符时，返回 false")
        void should_return_false_when_string_contains_non_hex_character() {
            boolean ret = UuidUtils.valid("00000000-0000-0000-0000-00000000000g");
            assertFalse(ret);
        }

        @Test
        @DisplayName("当字符串包含有效的UUID信息时，返回 true")
        void should_return_true_when_string_contains_valid_uuid() {
            boolean ret = UuidUtils.valid("12345678-90ab-cdef-1234-567890abcdef");
            assertTrue(ret);
        }
    }

    @Nested
    @DisplayName("测试 invalid 方法")
    class InvalidTest {
        @Test
        @DisplayName("当字符串为 null 时，返回 true")
        void should_return_true_when_string_is_null() {
            boolean ret = UuidUtils.invalid(null);
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符串长度不为36时，返回 true")
        void should_return_true_when_string_length_is_not_36() {
            boolean ret = UuidUtils.invalid("00000000-00000000-0000-000000000000");
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符串包含非16进制字符时，返回 true")
        void should_return_true_when_string_contains_non_hex_character() {
            boolean ret = UuidUtils.invalid("00000000-0000-0000-0000-00000000000g");
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符串包含有效的UUID信息时，返回 false")
        void should_return_false_when_string_contains_valid_uuid() {
            boolean ret = UuidUtils.invalid("12345678-90ab-cdef-1234-567890abcdef");
            assertFalse(ret);
        }
    }
}
