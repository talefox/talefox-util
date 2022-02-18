package xyz.talefox.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("测试 IoUtils 工具类")
class IoUtilsTest {
    @Nested
    @DisplayName("测试 resource 方法")
    class ResourceTest {
        @Test
        @DisplayName("当对象为 null 时，抛出异常")
        void should_throw_exception_when_object_is_null() {
            String message = assertThrows(IllegalArgumentException.class,
                    () -> IoUtils.resource((Object) null, "plain.txt")).getMessage();
            assertEquals("The object to lookup resource cannot be null.", message);
        }

        @Test
        @DisplayName("当类型为 null 时，抛出异常")
        void should_throw_exception_when_class_is_null() {
            String message = assertThrows(IllegalArgumentException.class,
                    () -> IoUtils.resource((Class<?>) null, "plain.txt")).getMessage();
            assertEquals("The class to lookup resource cannot be null.", message);
        }

        @Test
        @DisplayName("当类加载程序为 null 时，抛出异常")
        void should_throw_exception_when_class_loader_is_null() {
            String message = assertThrows(IllegalArgumentException.class,
                    () -> IoUtils.resource((ClassLoader) null, "plain.txt")).getMessage();
            assertEquals("The class loader to lookup resource cannot be null.", message);
        }

        @Test
        @DisplayName("当资源的键为 null 时，抛出异常")
        void should_throw_exception_when_resource_key_is_null() {
            String message = assertThrows(IllegalArgumentException.class,
                    () -> IoUtils.resource(IoUtilsTest.class, null)).getMessage();
            assertEquals("The key of resource to lookup cannot be null.", message);
        }

        @Test
        @DisplayName("通过对象可获取资源的输入流")
        void should_return_input_stream_from_object() throws IOException {
            Object obj = new Object() {};
            InputStream in = IoUtils.resource(obj, "/plain.txt");
            assertNotNull(in);
            in.close();
        }

        @Test
        @DisplayName("通过类型可获取资源的输入流")
        void should_return_input_stream_from_class() throws IOException {
            Object obj = IoUtilsTest.class;
            InputStream in = IoUtils.resource(obj, "/plain.txt");
            assertNotNull(in);
            in.close();
        }

        @Test
        @DisplayName("通过类加载程序可获取资源的输入流")
        void should_return_input_stream_from_class_loader() throws IOException {
            Object obj = IoUtilsTest.class.getClassLoader();
            InputStream in = IoUtils.resource(obj, "plain.txt");
            assertNotNull(in);
            in.close();
        }

        @Test
        @DisplayName("当资源不存在时，抛出异常")
        void should_throw_exception_when_resource_not_found() {
            String message = assertThrows(IllegalStateException.class,
                    () -> IoUtils.resource(IoUtilsTest.class, "not-found-resource")).getMessage();
            assertEquals("Resource not found in class loader. [key=not-found-resource]", message);
        }
    }
}
