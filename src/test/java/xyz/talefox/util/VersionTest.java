package xyz.talefox.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("测试 Version 工具类")
class VersionTest {
    @Nested
    @DisplayName("测试 create 方法")
    class CreateTest {
        @Test
        @DisplayName("当主版本号为负数时，抛出异常")
        void should_throw_exception_when_major_is_negative() {
            String message = assertThrows(IllegalArgumentException.class,
                    () -> Version.create(-1, 0)).getMessage();
            assertEquals("The major of a version cannot be negative.", message);
        }
        @Test
        @DisplayName("当次版本号为负数时，抛出异常")
        void should_throw_exception_when_minor_is_negative() {
            String message = assertThrows(IllegalArgumentException.class,
                    () -> Version.create(0, -1)).getMessage();
            assertEquals("The minor of a version cannot be negative.", message);
        }

        @Test
        @DisplayName("当修订版本号为负数时，抛出异常")
        void should_throw_exception_when_revision_is_negative() {
            String message = assertThrows(IllegalArgumentException.class,
                    () -> Version.create(0, 0, -1)).getMessage();
            assertEquals("The revision of a version cannot be negative.", message);
        }

        @Test
        @DisplayName("当构建版本号为负数时，抛出异常")
        void should_throw_exception_when_build_is_negative() {
            String message = assertThrows(IllegalArgumentException.class,
                    () -> Version.create(0, 0, 0, -1)).getMessage();
            assertEquals("The build of a version cannot be negative.", message);
        }
    }

    @Nested
    @DisplayName("测试 parse 方法")
    class ParseTest {
        @Test
        @DisplayName("当字符串中包含2部分版本号时，抛出异常")
        void should_throw_exception_when_only_contains_major_version() {
            String message = assertThrows(VersionFormatException.class,
                    () -> Version.parse("1")).getMessage();
            assertEquals("A version must have major and minor at least.", message);
        }

        @Test
        @DisplayName("当字符串中包含2部分版本号时，返回解析到的版本实例")
        void should_return_parsed_version_with_2_parts() {
            Version version = Version.parse("1.2");
            assertNotNull(version);
            assertEquals(1, version.major());
            assertEquals(2, version.minor());
            assertEquals(0, version.revision());
            assertEquals(0, version.build());
        }

        @Test
        @DisplayName("当字符串中包含3部分版本号时，返回解析到的版本实例")
        void should_return_parsed_version_with_3_parts() {
            Version version = Version.parse("1.2.3");
            assertNotNull(version);
            assertEquals(1, version.major());
            assertEquals(2, version.minor());
            assertEquals(3, version.revision());
            assertEquals(0, version.build());
        }

        @Test
        @DisplayName("当字符串中包含4部分版本号时，返回解析到的版本实例")
        void should_return_parsed_version_with_4_parts() {
            Version version = Version.parse("1.2.3.4");
            assertNotNull(version);
            assertEquals(1, version.major());
            assertEquals(2, version.minor());
            assertEquals(3, version.revision());
            assertEquals(4, version.build());
        }

        @Test
        @DisplayName("当字符串中包含5部分包版本号时，抛出异常")
        void should_throw_exception_when_more_than_4_parts() {
            String message = assertThrows(VersionFormatException.class,
                    () -> Version.parse("1.2.3.4.5")).getMessage();
            assertEquals("A version can only contain 4 parts at most. [actual=5]", message);
        }

        @Test
        @DisplayName("当主版本号不是非负整数时，抛出异常")
        void should_throw_exception_when_major_is_negative() {
            String message = assertThrows(VersionFormatException.class,
                    () -> Version.parse("-1.0.0.0")).getMessage();
            assertEquals("The major version must be a non-negative integer. [actual=-1]", message);
        }

        @Test
        @DisplayName("当次版本号不是非负整数时，抛出异常")
        void should_throw_exception_when_minor_is_negative() {
            String message = assertThrows(VersionFormatException.class,
                    () -> Version.parse("0.x.0.0")).getMessage();
            assertEquals("The minor version must be a non-negative integer. [actual=x]", message);
        }

        @Test
        @DisplayName("当修订版本号不是非负整数时，抛出异常")
        void should_throw_exception_when_revision_is_negative() {
            String message = assertThrows(VersionFormatException.class,
                    () -> Version.parse("0.0.a.0")).getMessage();
            assertEquals("The revision version must be a non-negative integer. [actual=a]", message);
        }

        @Test
        @DisplayName("当构建版本号不是非负整数时，抛出异常")
        void should_throw_exception_when_build_is_negative() {
            String message = assertThrows(VersionFormatException.class,
                    () -> Version.parse("0.0.0. 0 ")).getMessage();
            assertEquals("The build version must be a non-negative integer. [actual= 0 ]", message);
        }

        @Test
        @DisplayName("当字符串为 null 时，返回 null")
        void should_return_null_when_string_is_null() {
            Version version = Version.parse(null);
            assertNull(version);
        }

        @Test
        @DisplayName("当字符串仅包含空白字符时，返回 null")
        void should_return_null_when_string_is_blank() {
            Version version = Version.parse(" ");
            assertNull(version);
        }
    }

    @Nested
    @DisplayName("测试 equals 方法")
    class EqualsTest {
        @Test
        @DisplayName("当两个版本号包含相同的数据时，返回 true")
        void should_return_true_when_versions_contain_same_data() {
            Version v1 = Version.create(1, 2, 0);
            Version v2 = Version.create(1, 2);
            boolean ret = v1.equals(v2);
            assertTrue(ret);
        }

        @Test
        @DisplayName("当两个版本号包含不同的数据时，返回 false")
        void should_return_false_when_versions_contain_different_data() {
            Version v1 = Version.create(1, 2);
            Version v2 = Version.create(1, 2, 1);
            boolean ret = v1.equals(v2);
            assertFalse(ret);
        }

        @Test
        @DisplayName("当与不是 Version 类型的对象比较时，返回 false")
        void should_return_false_when_compare_with_an_object_that_is_not_a_version() {
            Version v1 = Version.create(1, 2);
            boolean ret = v1.equals(null);
            assertFalse(ret);
        }
    }

    @Nested
    @DisplayName("测试 hashCode 方法")
    class HashCodeTest {
        @Test
        @DisplayName("当两个版本号包含相同的数据时，返回相同的散列码")
        void should_return_true_when_versions_contain_same_data() {
            Version v1 = Version.create(1, 2, 0);
            Version v2 = Version.create(1, 2);
            assertEquals(v1.hashCode(), v2.hashCode());
        }

        @Test
        @DisplayName("当两个版本号包含不同的数据时，返回不同的散列码")
        void should_return_false_when_versions_contain_different_data() {
            Version v1 = Version.create(1, 2);
            Version v2 = Version.create(1, 2, 1);
            assertNotEquals(v1.hashCode(), v2.hashCode());
        }
    }

    @Nested
    @DisplayName("测试 toString 方法")
    class ToStringTest {
        @Test
        @DisplayName("当所有值均为0时，返回2位的版本字符串")
        void should_return_string_contains_major_and_minor_when_only_major_is_positive() {
            Version version = Version.create(0, 0, 0, 0);
            String text = version.toString();
            assertEquals("0.0", text);
        }

        @Test
        @DisplayName("当修订版本号为正数，且构建版本号为0时，返回3位的版本字符串")
        void should_return_string_contains_3_parts_when_revision_is_positive_and_build_is_zero() {
            Version version = Version.create(0, 0, 1, 0);
            String text = version.toString();
            assertEquals("0.0.1", text);
        }

        @Test
        @DisplayName("当构建版本号为正数时，返回4位的版本字符串")
        void should_return_string_contains_4_parts_when_build_is_positive() {
            Version version = Version.create(0, 0, 0, 1);
            String text = version.toString();
            assertEquals("0.0.0.1", text);
        }
    }

    @Nested
    @DisplayName("测试 compareTo 方法")
    class CompareTest {
        @Test
        @DisplayName("当两个版本号包含相同的数据时，返回 0")
        void should_return_zero_when_versions_contain_same_data() {
            Version v1 = Version.create(1, 0);
            Version v2 = Version.create(1, 0);
            int ret = v1.compareTo(v2);
            assertEquals(0, ret);
        }

        @Test
        @DisplayName("当第一个版本号大于第二个版本号时，返回一个正数")
        void should_return_positive_when_first_is_greater_than_second() {
            Version v1 = Version.create(0, 0, 0, 1);
            Version v2 = Version.create(0, 0, 0, 0);
            int ret = v1.compareTo(v2);
            assertTrue(ret > 0);
        }

        @Test
        @DisplayName("当第一个版本小小于第二个版本号时，返回一个负数")
        void should_return_negative_when_first_is_less_than_second() {
            Version v1 = Version.create(1, 2);
            Version v2 = Version.create(1, 12);
            int ret = v1.compareTo(v2);
            assertTrue(ret < 0);
        }
    }
}
