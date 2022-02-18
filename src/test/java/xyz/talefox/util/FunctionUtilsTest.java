package xyz.talefox.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("测试 FunctionUtils 工具类")
class FunctionUtilsTest {
    @Nested
    @DisplayName("测试 consumeIf(Object, Consumer) 方法")
    class ConsumeIfTest {
        @Test
        @DisplayName("当 consumer 为 null 时抛出异常")
        void should_throws_when_consumer_is_null() {
            String message = assertThrows(IllegalArgumentException.class,
                    () -> FunctionUtils.consumeIf(1, null)).getMessage();
            assertEquals("The consumer to consume value cannot be null.", message);
        }

        @Test
        @DisplayName("当 value 为 null 时不执行 consumer")
        void should_not_invoke_consumer_when_value_is_null() {
            Consumer<?> consumer = mock(Consumer.class);
            FunctionUtils.consumeIf(null, consumer);
        }

        @Test
        @DisplayName("当 value 不为 null 时执行 consumer")
        void should_invoke_consumer_when_value_is_not_null() {
            @SuppressWarnings("unchecked")
            Consumer<Integer> consumer = (Consumer<Integer>) mock(Consumer.class);
            FunctionUtils.consumeIf(1, consumer);
            verify(consumer, times(1)).accept(1);
        }
    }

    @Nested
    @DisplayName("测试 combine(Consumer, Consumer) 方法")
    class CombineTest {
        @Test
        @DisplayName("当第二个消费者为 null 时，返回第一个消费者")
        void should_return_first_when_second_is_null() {
            Consumer<?> consumer = mock(Consumer.class);
            Consumer<?> combined = FunctionUtils.combine(consumer, null);
            assertSame(consumer, combined);
        }

        @Test
        @DisplayName("当第一个消费者为 null 时，返回第二个消费者")
        void should_return_second_when_first_is_null() {
            Consumer<?> consumer = mock(Consumer.class);
            Consumer<?> combined = FunctionUtils.combine(null, consumer);
            assertSame(consumer, combined);
        }

        @Test
        @DisplayName("当两个消费者都不为 null 时，返回合并后的消费者")
        @SuppressWarnings({"rawtypes", "unchecked"})
        void should_return_combined_consumer() {
            Consumer consumer1 = mock(Consumer.class);
            Consumer consumer2 = mock(Consumer.class);
            when(consumer1.andThen(any())).thenCallRealMethod();
            when(consumer2.andThen(any())).thenCallRealMethod();
            Consumer combined = FunctionUtils.combine(consumer1, consumer2);
            assertNotNull(combined);
            Object value = new Object();
            combined.accept(value);
            verify(consumer1, times(1)).accept(value);
            verify(consumer2, times(1)).accept(value);
        }
    }

    @Nested
    @DisplayName("测试 emptyConsumer() 方法")
    class EmptyConsumerTest {
        @Test
        @DisplayName("返回可用的消费者")
        void should_return_available_consumer() {
            Consumer<?> consumer = FunctionUtils.emptyConsumer();
            assertNotNull(consumer);
            assertDoesNotThrow(() -> consumer.accept(null));
        }
    }
}
