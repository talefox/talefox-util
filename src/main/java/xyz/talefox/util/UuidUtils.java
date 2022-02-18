package xyz.talefox.util;

import java.util.stream.IntStream;

/**
 * 为UUID提供工具方法。
 *
 * @author 梁济时
 * @since 2021/10/3
 */
public final class UuidUtils {
    /** 隐藏默认构造方法，避免工具类被实例化。 */
    private UuidUtils() {}

    /**
     * 检查指定的字符序是否包含UUID信息。
     *
     * @param chars 表示待检查的字符序的 {@link CharSequence}。
     * @return 若字符序包含UUID信息，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean valid(CharSequence chars) {
        if (StringUtils.empty(chars) || chars.length() != 36) {
            return false;
        } else {
            return IntStream.of(8, 13, 18, 23).allMatch(index -> chars.charAt(index) == '-')
                    && StringUtils.hex(chars, 0, 8)
                    && StringUtils.hex(chars, 9, 4)
                    && StringUtils.hex(chars, 14, 4)
                    && StringUtils.hex(chars, 19, 4)
                    && StringUtils.hex(chars, 24, 12);
        }
    }

    /**
     * 检查指定的字符序是否不包含UUID信息。
     *
     * @param chars 表示待检查的字符序的 {@link CharSequence}。
     * @return 若字符序不包含UUID信息，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean invalid(CharSequence chars) {
        return !valid(chars);
    }
}
