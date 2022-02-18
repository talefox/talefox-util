package xyz.talefox.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * 为计算摘要提供工具方法。
 *
 * @author 梁济时
 * @since 2021/12/7
 */
public class DigestUtils {
    /**
     * 隐藏默认构造方法，避免工具类被实例化。
     */
    private DigestUtils() {
        // Hide visibility.
    }

    /**
     * 使用指定的算法计算输入流中包含数据的摘要信息。
     *
     * @param algorithm 表示摘要算法名称的 {@link String}。
     * @param in 表示待计算摘要信息的输入流的 {@link InputStream}。
     * @param bufferSize 表示计算摘要信息时使用的缓存的大小的32位整数。
     * @return 表示输入流中包含信息的摘要信息的 {@link String}。
     * @throws IllegalArgumentException {@code algorithm} 是空白字符串，或 {@code in} 为 {@code null}，或 {@code bufferSize} 不是一个正整数。
     * @throws IllegalStateException {@code algorithm} 表示的算法不存在。
     * @throws IOException 读取输入过程发生输入输出异常。
     */
    public static String digest(String algorithm, InputStream in, int bufferSize) throws IOException {
        Validation.notBlank(algorithm, "The algorithm to compute digest cannot be blank.");
        Validation.notNull(in, "The input stream to compute digest cannot be null.");
        Validation.greaterThan(bufferSize, 0, "The size of buffer must be positive.");
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(String.format(Locale.ROOT,
                    "The algorithm to compute digest not found. [algorithm=%s]", algorithm), ex);
        }
        byte[] buffer = new byte[bufferSize];
        int read;
        while ((read = in.read(buffer, 0, bufferSize)) > -1) {
            digest.update(buffer, 0, read);
        }
        byte[] result = digest.digest();
        return StringUtils.toHexString(result);
    }
}
