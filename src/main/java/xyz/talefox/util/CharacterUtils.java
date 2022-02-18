package xyz.talefox.util;

/**
 * 为字符提供工具方法。
 *
 * @author 梁济时
 * @since 2021/1/7
 */
public class CharacterUtils {
    /** 隐藏默认构造方法，避免工具类被实例化。 */
    private CharacterUtils() {}

    /**
     * 检查指定字符是否是一个有效字符。
     *
     * @param value 表示待检查的字符。
     * @param effective 表示有效字符的数组。
     * @return 若是有效字符，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean in(char value, char... effective) {
        if (effective != null && effective.length >= 1) {
            for (char c : effective) {
                if (value == c) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查指定字符是否在指定的区间内。
     *
     * @param value 表示待检查的字符。
     * @param minimum 表示有效字符的最小值。最小值在有效区间内。
     * @param maximum 表示有效字符的最大值。最大值在有效区间内。
     * @return 若字符在有效区间内，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean between(char value, char minimum, char maximum) {
        return value >= minimum && value <= maximum;
    }

    /**
     * 检查指定的字符是否是一个十六进制字符。
     *
     * @param ch 表示待检查的字符。
     * @return 若是十六进制字符，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean hex(char ch) {
        return between(ch, '0', '9') || between(ch, 'a', 'f') || between(ch, 'A', 'F');
    }
}
