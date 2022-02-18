package xyz.talefox.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("测试 Validation 工具类")
class ValidationTest {
    @Nested
    @DisplayName("测试通用对象工具方法")
    class ObjectValidationTest {
        @Test
        @DisplayName("throw IllegalArgumentException when value is null and message is null")
        void should_throws_when_value_is_null_for_null_message() {
            assertThrows(IllegalArgumentException.class,
                    () -> Validation.notNull(null, (String) null));
        }

        @Test
        @DisplayName("throw IllegalArgumentException when value is null and supplier is null")
        void should_throws_when_value_is_null_for_null_supplier() {
            assertThrows(IllegalArgumentException.class,
                    () -> Validation.notNull(null, (Supplier<? extends RuntimeException>) null));
        }

        @Test
        @DisplayName("throw IllegalStateException when value is null and supplier return IllegalStateException")
        void should_throws_when_value_is_null() {
            assertThrows(IllegalStateException.class,
                    () -> Validation.notNull(null, IllegalStateException::new));
        }

        @Test
        @DisplayName("return original value when not null")
        void should_return_value_when_not_null() {
            Object value = new byte[0];
            Object validated = Validation.notNull(value, (String) null);
            assertSame(value, validated);
        }
    }

    @Nested
    @DisplayName("测试String对象工具方法")
    class StringValidationTest {
        private static final String BLANK = " ";
        private static final String NOT_BLANK = "x";

        @Test
        @DisplayName("throw IllegalArgumentException when value is blank and message is null")
        void should_throws_when_value_is_blank_for_null_message() {
            assertThrows(IllegalArgumentException.class,
                    () -> Validation.notBlank(BLANK, (String) null));
        }

        @Test
        @DisplayName("throw IllegalArgumentException when value is blank and supplier is null")
        void should_throws_when_value_is_blank_for_null_supplier() {
            assertThrows(IllegalArgumentException.class,
                    () -> Validation.notBlank(BLANK, (Supplier<? extends RuntimeException>) null));
        }

        @Test
        @DisplayName("throw IllegalStateException when value is blank and supplier return IllegalStateException")
        void should_throws_when_value_is_null() {
            assertThrows(IllegalStateException.class,
                    () -> Validation.notBlank(BLANK, IllegalStateException::new));
        }

        @Test
        @DisplayName("return original value when not blank")
        void should_return_value_when_not_null() {
            Object validated = Validation.notNull(NOT_BLANK, (String) null);
            assertSame(NOT_BLANK, validated);
        }
    }

    @Nested
    @DisplayName("测试数组对象工具方法")
    class ArrayValidationTest {
        private final Integer[] empty = new Integer[0];
        private final Integer[] notEmpty = new Integer[] { 1 };

        @Test
        @DisplayName("throw IllegalArgumentException when value is not empty array and message is null")
        void should_throws_when_value_is_not_empty_for_null_message() {
            assertThrows(IllegalArgumentException.class,
                    () -> Validation.empty(notEmpty, (String) null));
        }

        @Test
        @DisplayName("throw IllegalArgumentException when value is not empty array and supplier is null")
        void should_throws_when_value_is_not_empty_for_null_supplier() {
            assertThrows(IllegalArgumentException.class,
                    () -> Validation.empty(notEmpty, (Supplier<? extends RuntimeException>) null));
        }

        @Test
        @DisplayName("throw IllegalStateException when value is not empty array and supplier return IllegalStateException")
        void should_throws_when_value_is_not_empty() {
            assertThrows(IllegalStateException.class,
                    () -> Validation.empty(notEmpty, IllegalStateException::new));
        }

        @Test
        @DisplayName("return original value when empty array")
        void should_return_value_when_empty() {
            Object validated = Validation.empty(empty, (String) null);
            assertSame(empty, validated);
        }

        @Test
        @DisplayName("throw IllegalArgumentException when value is empty array and message is null")
        void should_throws_when_value_is_empty_for_null_message() {
            assertThrows(IllegalArgumentException.class,
                    () -> Validation.notEmpty(empty, (String) null));
        }

        @Test
        @DisplayName("throw IllegalArgumentException when value is empty array and supplier is null")
        void should_throws_when_value_is_empty_for_null_supplier() {
            assertThrows(IllegalArgumentException.class,
                    () -> Validation.notEmpty(empty, (Supplier<? extends RuntimeException>) null));
        }

        @Test
        @DisplayName("throw IllegalStateException when value is empty array and supplier return IllegalStateException")
        void should_throws_when_value_is_empty() {
            assertThrows(IllegalStateException.class,
                    () -> Validation.notEmpty(empty, IllegalStateException::new));
        }

        @Test
        @DisplayName("return original value when not empty array")
        void should_return_value_when_not_empty() {
            Object validated = Validation.notEmpty(notEmpty, (String) null);
            assertSame(notEmpty, validated);
        }
    }
}
