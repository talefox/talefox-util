package xyz.talefox.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("测试 DigestUtils 工具类")
class DigestUtilsTest {
    private static final String ALGORITHM = "MD5";
    private static final String DATA = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BUFFER_SIZE = 32;
    private static final String DIGEST = "76658de2ac7d406f93dfbe8bb6d9f549";

    private InputStream in;

    @BeforeEach
    void setup() {
        this.in = new ByteArrayInputStream(DATA.getBytes(StandardCharsets.UTF_8));
    }

    @AfterEach
    void teardown() throws IOException {
        this.in.close();
    }

    @Test
    @DisplayName("当 algorithm 为空白字符串时抛出异常")
    void should_throws_when_algorithm_is_blank() {
        String message = assertThrows(IllegalArgumentException.class,
                () -> DigestUtils.digest(" ", this.in, BUFFER_SIZE)).getMessage();
        assertEquals("The algorithm to compute digest cannot be blank.", message);
    }

    @Test
    @DisplayName("当 in 为 null 时抛出异常")
    void should_throws_when_input_stream_is_null() {
        String message = assertThrows(IllegalArgumentException.class,
                () -> DigestUtils.digest(ALGORITHM, null, BUFFER_SIZE)).getMessage();
        assertEquals("The input stream to compute digest cannot be null.", message);
    }

    @Test
    @DisplayName("当 bufferSize 不是正整数时抛出异常")
    void should_throws_when_buffer_size_is_not_positive() {
        String message = assertThrows(IllegalArgumentException.class,
                () -> DigestUtils.digest(ALGORITHM, this.in, 0)).getMessage();
        assertEquals("The size of buffer must be positive.", message);
    }

    @Test
    @DisplayName("当 algorithm 不存在时抛出异常")
    void should_throws_when_algorithm_is_not_found() {
        String message = assertThrows(IllegalStateException.class,
                () -> DigestUtils.digest("MD0", this.in, BUFFER_SIZE)).getMessage();
        assertEquals("The algorithm to compute digest not found. [algorithm=MD0]", message);
    }

    @Test
    @DisplayName("当入参正确时返回数据的摘要")
    void should_compute_digest() throws IOException {
        String digest = DigestUtils.digest(ALGORITHM, this.in, BUFFER_SIZE);
        assertEquals(DIGEST, digest);
    }
}
