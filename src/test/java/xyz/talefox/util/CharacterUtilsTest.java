package xyz.talefox.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 为 {@link CharacterUtils} 提供单元测试。
 */
@DisplayName("测试 CharacterUtils 工具类")
class CharacterUtilsTest {
    @Nested
    @DisplayName("测试 in(char[], char) 方法")
    class InTest {
        @Test
        @DisplayName("当字符在数组中时返回true")
        void should_return_true_when_value_in_specific_characters() {
            char[] chs = new char[] { 'a', 'b', 'c' };
            char ch = 'b';
            boolean ret = CharacterUtils.in(ch, chs);
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符不在数组中时返回false")
        void should_return_false_when_value_not_in_specific_characters() {
            char[] chs = new char[] { 'a', 'b', 'c' };
            char ch = 'd';
            boolean ret = CharacterUtils.in(ch, chs);
            assertFalse(ret);
        }
    }

    @Nested
    @DisplayName("测试 between(char, char, char) 方法")
    class BetweenTest {
        @Test
        @DisplayName("当字符是有效区间的最小值时返回true")
        void should_return_true_when_value_is_lower_bound_of_effect_range() {
            char minimum = '0';
            char maximum = '9';
            boolean ret = CharacterUtils.between(minimum, minimum, maximum);
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符是有效区间的最大值时返回true")
        void should_return_true_when_value_is_upper_bound_of_effect_range() {
            char minimum = '0';
            char maximum = '9';
            boolean ret = CharacterUtils.between(maximum, minimum, maximum);
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符不在有效区间时返回false")
        void should_return_false_when_value_out_of_effect_range() {
            char minimum = 'A';
            char maximum = 'Z';
            char value = 'a';
            boolean ret = CharacterUtils.between(value, minimum, maximum);
            assertFalse(ret);
        }
    }

    @Nested
    @DisplayName("测试 hex(char) 方法")
    class HexTest {
        @Test
        @DisplayName("当字符为“0”时返回true")
        void should_return_true_when_value_is_character_0() {
            boolean ret = CharacterUtils.hex('0');
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符为“9”时返回true")
        void should_return_true_when_value_is_character_9() {
            boolean ret = CharacterUtils.hex('9');
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符为小于“0”的字符时返回false")
        void should_return_false_when_value_is_less_than_character_0() {
            boolean ret = CharacterUtils.hex((char) ('0' - 1));
            assertFalse(ret);
        }

        @Test
        @DisplayName("当字符为大于“9”的字符时返回false")
        void should_return_false_when_value_is_greater_than_character_9() {
            boolean ret = CharacterUtils.hex((char) ('9' + 1));
            assertFalse(ret);
        }

        @Test
        @DisplayName("当字符为”a“时返回true")
        void should_return_true_when_value_is_character_lower_a() {
            boolean ret = CharacterUtils.hex('a');
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符为“f”时返回true")
        void should_return_true_when_value_is_character_lower_f() {
            boolean ret = CharacterUtils.hex('f');
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符为小于“a”的字符时返回false")
        void should_return_false_when_value_is_less_than_lower_a() {
            boolean ret = CharacterUtils.hex((char) ('a' - 1));
            assertFalse(ret);
        }

        @Test
        @DisplayName("当字符为大于“f”的字符时返回false")
        void should_return_false_when_value_is_greater_than_lower_f() {
            boolean ret = CharacterUtils.hex((char) ('f' + 1));
            assertFalse(ret);
        }

        @Test
        @DisplayName("当字符为”A“时返回true")
        void should_return_true_when_value_is_character_upper_a() {
            boolean ret = CharacterUtils.hex('A');
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符为“F”时返回true")
        void should_return_true_when_value_is_character_upper_f() {
            boolean ret = CharacterUtils.hex('F');
            assertTrue(ret);
        }

        @Test
        @DisplayName("当字符为小于“A”的字符时返回false")
        void should_return_false_when_value_is_less_than_upper_a() {
            boolean ret = CharacterUtils.hex((char) ('A' - 1));
            assertFalse(ret);
        }

        @Test
        @DisplayName("当字符为大于“F”的字符时返回false")
        void should_return_false_when_value_is_greater_than_upper_f() {
            boolean ret = CharacterUtils.hex((char) ('F' + 1));
            assertFalse(ret);
        }
    }
}
