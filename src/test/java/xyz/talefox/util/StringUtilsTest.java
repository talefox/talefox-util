package xyz.talefox.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("测试 StringUtils 工具类")
class StringUtilsTest {
    @Nested
    @DisplayName("测试 numeric 方法")
    class NumericTest {
        @Test
        @DisplayName("当待测试的字符串为null时返回false")
        void should_return_false_when_numeric_string_is_null() {
            assertFalse(StringUtils.numeric(null, true, true));
        }

        @Test
        @DisplayName("当待测试的字符串为空时返回false")
        void should_return_false_when_numeric_string_is_empty() {
            assertFalse(StringUtils.numeric(StringUtils.empty(), true, true));
        }

        @Test
        @DisplayName("当待测试的字符串仅包含一个正负号时返回false")
        void should_return_false_when_numeric_string_contains_only_sign() {
            assertFalse(StringUtils.numeric("+", true, true));
        }

        @Test
        @DisplayName("当待测试的字符串仅包含一个点时返回false")
        void should_return_false_when_numeric_string_contains_only_dot() {
            assertFalse(StringUtils.numeric(".", true, true));
        }

        @Test
        @DisplayName("当待测试的字符串仅包含正负号和点时返回false")
        void should_return_false_when_numeric_string_contains_only_sign_and_dot() {
            assertFalse(StringUtils.numeric("-.", true, true));
        }

        @Test
        @DisplayName("当待测试的字符串包含负号，但期望为无负号数时返回false")
        void should_return_false_when_unsigned_numeric_string_contains_sign() {
            assertFalse(StringUtils.numeric("-1", false, true));
        }

        @Test
        @DisplayName("当待测试的字符串是一个小数，但期望其为整数时返回false")
        void should_return_false_when_integer_string_contains_dot() {
            assertFalse(StringUtils.numeric("1.0", true, false));
        }

        @Test
        @DisplayName("当待测试的字符串的正负号不再第一个字符时返回false")
        void should_return_false_when_sign_not_at_first_of_numeric_string() {
            assertFalse(StringUtils.numeric("1-", true, true));
        }

        @Test
        @DisplayName("当以小数点结尾时返回true")
        void should_return_true_when_numeric_string_end_with_dot() {
            assertTrue(StringUtils.numeric("+1.", true, true));
        }

        @Test
        @DisplayName("当以小数点开头时返回true")
        void should_return_true_when_numeric_string_start_with_dot() {
            assertTrue(StringUtils.numeric("-.1", true, true));
        }

        @Test
        @DisplayName("当字符串的内容是一个带有正负号和小数点的数字时返回true")
        void should_return_true_when_numeric_string_is_general() {
            assertTrue(StringUtils.numeric("-1.0", true, true));
        }

        @Test
        @DisplayName("当待测试的字符串为null，且指定了开始和结束位置时返回false（不抛异常）")
        void should_return_false_when_numeric_string_with_bounds_is_null() {
            assertFalse(StringUtils.numeric(null, 0, 0, true, true));
        }

        @Test
        @DisplayName("当待检查的字符串的区间的开始位置是一个负数时抛出异常")
        void should_throws_when_start_of_numeric_string_is_negative() {
            assertThrows(IllegalArgumentException.class,
                    () -> StringUtils.numeric("1.0", -1, 1, true, true));
        }

        @Test
        @DisplayName("当待检查的字符串的区间的结束位置超出字符串长度时抛出异常")
        void should_throws_when_end_of_numeric_string_is_out_of_bounds() {
            assertThrows(IllegalArgumentException.class,
                    () -> StringUtils.numeric("1.0", 0, 100, true, true));
        }

        @Test
        @DisplayName("当待检查的字符串的区间的结束位置在开始位置之前时抛出异常")
        void should_throws_when_end_is_less_than_start_of_numeric_string() {
            assertThrows(IllegalArgumentException.class,
                    () -> StringUtils.numeric("1.0", 1, 0, true, true));
        }

    }

    @Nested
    @DisplayName("测试 nullOrEmpty 方法")
    class EmptyTest {
        @Test
        @DisplayName("当字符串既不为 null，也不是空字符串时，返回 false")
        void should_return_false_when_string_is_blank_but_not_empty() {
            boolean ret = StringUtils.empty(" ");
            assertFalse(ret);
        }

        @Test
        @DisplayName("当字符串为 null 时，返回 true")
        void should_return_true_when_string_is_null() {
            boolean ret = StringUtils.empty(null);
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符串为空字符串时，返回 true")
        void should_return_true_when_string_is_empty() {
            boolean ret = StringUtils.empty("");
            assertTrue(ret);
        }
    }

    @Nested
    @DisplayName("测试 notEmpty 方法")
    class NotEmptyTest {
        @Test
        @DisplayName("当字符串既不为 null，也不是空字符串时，返回 true")
        void should_return_false_when_string_is_blank_but_not_empty() {
            boolean ret = StringUtils.notEmpty(" ");
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符串为 null 时，返回 true")
        void should_return_true_when_string_is_null() {
            boolean ret = StringUtils.notEmpty(null);
            assertFalse(ret);
        }

        @Test
        @DisplayName("当字符串为空字符串时，返回 true")
        void should_return_true_when_string_is_empty() {
            boolean ret = StringUtils.notEmpty("");
            assertFalse(ret);
        }
    }

    @Nested
    @DisplayName("测试 blank 方法")
    class BlankTest {
        @Test
        @DisplayName("当字符串为 null 时，返回 true")
        void should_return_true_when_string_is_null() {
            boolean ret = StringUtils.blank(null);
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符串为空字符串时，返回 true")
        void should_return_true_when_string_is_empty() {
            boolean ret = StringUtils.blank("");
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符串不为 null 也不是空字符串，但只包含空白字符时，返回 true")
        void should_return_true_when_string_is_blank_but_not_empty() {
            boolean ret = StringUtils.blank(" ");
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符串包含非空白字符时，返回 false")
        void should_return_false_when_string_is_not_blank() {
            boolean ret = StringUtils.blank(" a ");
            assertFalse(ret);
        }
    }

    @Nested
    @DisplayName("测试 notBlank 方法")
    class NotBlankTest {
        @Test
        @DisplayName("当字符串为 null 时，返回 false")
        void should_return_false_when_string_is_null() {
            boolean ret = StringUtils.notBlank(null);
            assertFalse(ret);
        }

        @Test
        @DisplayName("当字符串为空字符串时，返回 false")
        void should_return_false_when_string_is_empty() {
            boolean ret = StringUtils.notBlank("");
            assertFalse(ret);
        }

        @Test
        @DisplayName("当字符串不为 null 也不是空字符串，但只包含空白字符时，返回 false")
        void should_return_false_when_string_is_blank_but_not_empty() {
            boolean ret = StringUtils.notBlank(" ");
            assertFalse(ret);
        }

        @Test
        @DisplayName("当字符串包含非空白字符时，返回 true")
        void should_return_true_when_string_is_not_blank() {
            boolean ret = StringUtils.notBlank(" a ");
            assertTrue(ret);
        }
    }

    @Nested
    @DisplayName("测试 equalsIgnoreCase 方法")
    class CompareTest {
        @Test
        @DisplayName("当两个字符串包含相同的数据，但是大小写不同时，返回 true")
        void should_return_true_when_s1_equals_ignore_case_s2() {
            String s1 = "abc";
            String s2 = "ABC";
            boolean ret = StringUtils.equalsIgnoreCase(s1, s2);
            assertTrue(ret);
        }

        @Test
        @DisplayName("当两个字符串，在忽略大小写的情况下也包含不同数据时，返回 false")
        void should_return_false_when_s1_does_not_contain_the_same_data_with_s2() {
            String s1 = "abc";
            String s2 = "Bc";
            boolean ret = StringUtils.equalsIgnoreCase(s1, s2);
            assertFalse(ret);
        }

        @Test
        @DisplayName("当第一个字符串为 null，但第二个字符串不为 null 时，返回 false")
        void should_return_false_when_s1_is_null_but_s2_is_not_null() {
            boolean ret = StringUtils.endsWithIgnoreCase(null, "s2");
            assertFalse(ret);
        }

        @Test
        @DisplayName("当第一个字符串不为 null，但第二个字符串为 null 时，返回 false")
        void should_return_false_when_s1_is_not_null_but_s2_is_null() {
            boolean ret = StringUtils.endsWithIgnoreCase("s1", null);
            assertFalse(ret);
        }

        @Test
        @DisplayName("当两个字符串都为 null 时，返回 true")
        void should_return_true_when_s1_and_s2_are_all_null() {
            boolean ret = StringUtils.equalsIgnoreCase(null, null);
            assertTrue(ret);
        }
    }

    @Nested
    @DisplayName("测试 endsWithIgnoreCase 方法")
    class EndsWithIgnoreCaseTest {
        @Test
        @DisplayName("当字符串以后缀结尾，但是大小写不同时，返回 true")
        void should_return_true_when_s1_ends_with_s2() {
            boolean ret = StringUtils.endsWithIgnoreCase("Abc", "BC");
            assertTrue(ret);
        }

        @Test
        @DisplayName("当原字符串不以后缀结尾时，返回 false")
        void should_return_false_s1_does_not_end_with_s2() {
            boolean ret = StringUtils.endsWithIgnoreCase("aBc", "Cb");
        }

        @Test
        @DisplayName("当原字符串不为 null，但是待检查的后缀为 null 时，返回false")
        void should_return_false_when_the_suffix_is_null() {
            boolean ret = StringUtils.endsWithIgnoreCase("s1", null);
            assertFalse(ret);
        }

        @Test
        @DisplayName("当原字符串为 null，但是后缀不为 null 时，返回false")
        void should_return_false_when_string_is_null_but_suffix_is_not_null() {
            boolean ret = StringUtils.endsWithIgnoreCase(null, "s2");
            assertFalse(ret);
        }

        @Test
        @DisplayName("当原字符串和后缀均为 null 时，返回 true")
        void should_return_true_when_string_and_suffix_are_all_null() {
            boolean ret = StringUtils.endsWithIgnoreCase(null, null);
            assertTrue(ret);
        }
    }

    @Nested
    @DisplayName("测试 join 方法")
    class JoinTest {
        @Test
        @DisplayName("返回以分隔符拼接后的字符串")
        void should_return_joined_string() {
            String joined = StringUtils.join(',', 1, 2, 3, null);
            String expected = "1,2,3,null";
            assertEquals(expected, joined);
        }

        @Test
        @DisplayName("当待拼接的字符串数组为 null 时，返回空字符串")
        void should_return_empty_string_when_input_empty_arguments() {
            String joined = StringUtils.join(',', (Object[]) null);
            assertNotNull(joined);
            assertTrue(joined.isEmpty());
        }

        @Test
        @DisplayName("当分隔符为空字符串时，返回连接的字符串")
        void should_return_concat_string_with_separator_is_empty() {
            String joined = StringUtils.join("", "a", "B");
            assertEquals("aB", joined);
        }

        @Test
        @DisplayName("当待拼接的内容不是字符串时，首先将其 toString，再拼接")
        void should_return_joined_to_string_values() {
            String joined = StringUtils.join('|', Arrays.asList(1, 2));
            assertEquals("1|2", joined);
        }

        @Test
        @DisplayName("使用提供的方法将元素转为字符串后再拼接")
        void should_return_joined_mapped_string_values() {
            String joined = StringUtils.join('|', Arrays.asList(0xa, 0xb), Integer::toHexString);
            assertEquals("a|b", joined);
        }
    }

    @Nested
    @DisplayName("测试 upper 方法")
    class UpperTest {
        @Test
        @DisplayName("返回原字符串的全大写形式")
        void should_return_upper_string() {
            String result = StringUtils.upper("xYz");
            assertEquals("XYZ", result);
        }

        @Test
        @DisplayName("当原字符串为 null 时，返回 null")
        void should_return_null_when_string_is_null() {
            String upper = StringUtils.upper(null);
            assertNull(upper);
        }
    }

    @Nested
    @DisplayName("测试 lower 方法")
    class LowerTest {
        @Test
        @DisplayName("返回原字符串的全小写形式")
        void should_return_lower_string() {
            String result = StringUtils.lower("xYz");
            assertEquals("xyz", result);
        }

        @Test
        @DisplayName("当原字符串为 null 时，返回 null")
        void should_return_null_when_string_is_null() {
            String upper = StringUtils.lower(null);
            assertNull(upper);
        }
    }

    @Nested
    @DisplayName("测试 blankIf 方法")
    class BlankIfTest {
        @Test
        @DisplayName("当原字符串不是空白字符串时，返回原字符串")
        void should_return_non_blank_value() {
            String value = StringUtils.blankIf(" x ", "y");
            assertEquals(" x ", value);
        }

        @Test
        @DisplayName("当原字符串是空白字符串时，使用默认字符串")
        void should_return_non_blank_default_value() {
            String value = StringUtils.blankIf(" ", "y");
            assertEquals("y", value);
        }

        @Test
        @DisplayName("当原字符串不是空白字符串，且默认值提供程序为 null 时，返回原字符串")
        void should_return_non_blank_value_even_default_supplier_is_null() {
            String value = StringUtils.blankIf(" x", (Supplier<String>) null);
            assertEquals(" x", value);
        }

        @Test
        @DisplayName("当原字符串不为 null，但仅包含空白字符串时，返回默认的 null")
        void should_return_null_when_value_is_blank_and_default_supplier_return_null() {
            String value = StringUtils.blankIf(" ", () -> null);
            assertNull(value);
        }

        @Test
        @DisplayName("当原字符串为 null，且默认值提供程序为 null 时，抛出异常")
        void should_throws_when_value_is_blank_and_default_supplier_is_null() {
            String message = assertThrows(IllegalArgumentException.class,
                    () -> StringUtils.blankIf(null, (Supplier<String>) null)).getMessage();
            assertEquals("The supplier of default value cannot be null.", message);
        }
    }

    @Nested
    @DisplayName("测试 split 方法")
    class SplitTest {
        @Test
        @DisplayName("当原字符串为 null 时，返回空的字符串列表")
        void should_return_empty_strings_when_split_null() {
            List<String> parts = StringUtils.split(',', null);
            assertNotNull(parts);
            assertTrue(parts.isEmpty());
        }

        @Test
        @DisplayName("当字符串仅包含一个分隔符时，返回两个空字符串组成的列表")
        void should_return_parts_with_empty_entries() {
            String origin = ",";
            List<String> parts = StringUtils.split(',', origin);
            assertEquals(2, parts.size());
            assertTrue(parts.get(0).isEmpty());
            assertTrue(parts.get(1).isEmpty());
        }

        @Test
        @DisplayName("分割字符串并返回非空条目")
        void should_return_parts_without_empty_entries() {
            String origin = "a,,b";
            List<String> parts = StringUtils.split(origin, ',', true);
            assertEquals(2, parts.size());
            assertEquals("a", parts.get(0));
            assertEquals("b", parts.get(1));
        }
    }

    @Nested
    @DisplayName("测试 lengthBetween 方法")
    class LengthCheckTest {
        @Test
        @DisplayName("return true when length is minimum")
        void should_return_true_when_length_of_string_is_minimum() {
            boolean ret = StringUtils.lengthBetween("x", 1, 2);
            assertTrue(ret);
        }

        @Test
        @DisplayName("return true when length is maximum")
        void should_return_true_when_length_of_string_is_maximum() {
            boolean ret = StringUtils.lengthBetween("x", 0, 1);
            assertTrue(ret);
        }

        @Test
        @DisplayName("return false when length out of range")
        void should_return_false_when_length_of_string_out_of_range() {
            boolean ret = StringUtils.lengthBetween("x", 2, 3);
            assertFalse(ret);
        }
        @Test
        @DisplayName("return false when length is minimum")
        void should_return_false_when_length_of_string_is_minimum() {
            boolean ret = StringUtils.lengthNotBetween("x", 1, 2);
            assertFalse(ret);
        }

        @Test
        @DisplayName("return false when length is maximum")
        void should_return_false_when_length_of_string_is_maximum() {
            boolean ret = StringUtils.lengthNotBetween("x", 0, 1);
            assertFalse(ret);
        }

        @Test
        @DisplayName("return true when length out of range")
        void should_return_true_when_length_of_string_out_of_range() {
            boolean ret = StringUtils.lengthNotBetween("x", 2, 3);
            assertTrue(ret);
        }
    }

    @Nested
    @DisplayName("测试 chars 方法")
    class ToCharArrayTest {
        @Test
        @DisplayName("null array of null character sequence")
        void should_return_null_when_char_sequence_is_null() {
            assertNull(StringUtils.chars(null));
        }

        @Test
        @DisplayName("array of character sequence")
        void should_return_array_of_character_sequence() {
            char[] chs = StringUtils.chars("abc");
            assertArrayEquals(new char[] { 'a', 'b', 'c' }, chs);
        }
    }

    @Nested
    @DisplayName("测试 trim 方法")
    class TrimTest {
        @Test
        @DisplayName("return null when trim null")
        void should_return_null_when_trim_null() {
            assertNull(StringUtils.trim(null));
        }

        @Test
        @DisplayName("return trimmed string")
        void should_return_trimmed_string() {
            String result = StringUtils.trim(" abc ");
            assertEquals("abc", result);
        }
    }

    @Nested
    @DisplayName("测试 contains 方法")
    class ContainsTest {
        @Test
        @DisplayName("当字符序中包含字符时，返回 true")
        void should_return_true_when_sequence_contains_character() {
            boolean ret = StringUtils.contains("aBc", 'B');
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符序中不包含字符时，返回 false")
        void should_return_false_when_sequence_does_not_contain_character() {
            boolean ret = StringUtils.contains("abc", 'B');
            assertFalse(ret);
        }
    }

    @Nested
    @DisplayName("测试 hex 方法")
    class HexTest {
        @Test
        @DisplayName("当字符序中的所有字符都是16进制字符时，返回 true")
        void should_return_true_when_characters_in_sequence_are_all_hex() {
            boolean ret = StringUtils.hex("01234567890abcdef");
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符序中包含非16进制字符时，返回 false")
        void should_return_true_when_sequence_contains_non_hex_character() {
            boolean ret = StringUtils.hex("abcdefg");
            assertFalse(ret);
        }
    }

    @Nested
    @DisplayName("测试 inIgnoreCase 方法")
    class InIgnoreCaseTest {
        @Test
        @DisplayName("当值在可选值中时，返回 true")
        void should_return_true_when_value_in_available_values() {
            boolean ret = StringUtils.inIgnoreCase("Def", "Abc", "def");
            assertTrue(ret);
        }

        @Test
        @DisplayName("当值不在可选值中时，返回 false")
        void should_return_false_when_value_not_in_available_values() {
            boolean ret = StringUtils.inIgnoreCase("abc", "bcd", "cde");
            assertFalse(ret);
        }
    }

    @Nested
    @DisplayName("测试 trimStart 方法")
    class TrimStartTest {
        @Test
        @DisplayName("当值是空字符串时，返回原始字符串")
        void should_return_origin_string_when_empty() {
            String origin = "";
            String trimmed = StringUtils.trimStart(origin);
            assertSame(origin, trimmed);
        }

        @Test
        @DisplayName("当值中仅存在空白字符时，返回空字符串")
        void should_return_empty_string_when_value_contains_only_blank_characters() {
            String origin = "  ";
            String trimmed = StringUtils.trimStart(origin);
            assertNotNull(trimmed);
            assertTrue(trimmed.isEmpty());
        }

        @Test
        @DisplayName("当值中不仅包含空白字符时，返回开始位置的空白字符被截断的字符串")
        void should_return_start_trimmed_string() {
            String origin = "  a  ";
            String trimmed = StringUtils.trimStart(origin);
            assertEquals("a  ", trimmed);
        }
    }
}
