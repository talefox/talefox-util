package xyz.talefox.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("测试 LazyLoader 工具类")
class LazyLoaderTest {
    @Test
    @DisplayName("多次调用返回相同的实例")
    void should_return_same_instance() {
        Object value = new byte[0];
        Supplier<Object> supplier = () -> {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                fail("Failed to sleep creation thread.");
            }
            return value;
        };
        LazyLoader<Object> loader = new LazyLoader<>(supplier);
        AtomicReference<Object> r1 = new AtomicReference<>();
        AtomicReference<Object> r2 = new AtomicReference<>();
        Thread t1 = new Thread(() -> r1.set(loader.get()));
        Thread t2 = new Thread(() -> r2.set(loader.get()));
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            fail("Failed to join loading threads.");
        }
        assertSame(r1.get(), r2.get());
    }
}
