package xyz.talefox.util;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * 为字符串提供工具方法。
 *
 * @author 梁济时
 * @since 2020/1/2
 */
public class StringUtils {
    private static final String EMPTY = "";

    /** 隐藏默认构造方法，避免工具类被实例化。 */
    private StringUtils() {}

    /**
     * 获取空字符串。
     *
     * @return 表示空字符串的 {@link String}。
     */
    public static String empty() {
        return EMPTY;
    }

    /**
     * 将指定对象转为字符串表现形式。
     *
     * @param obj 表示原始对象的 {@link Object}。
     * @return 若原始对象为 {@code null}，则为 {@code null}；否则为其{@link Object#toString() 字符串表现形式}的 {@link String}。
     */
    public static String toString(Object obj) {
        return obj == null ? null : obj.toString();
    }

    /**
     * 检查指定的字符序是否为 {@code null} 或不存在任何字符。
     *
     * @param chars 表示待检查的字符序的 {@link CharSequence}。
     * @return 若字符序为 {@code null} 或不包含任何字符，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean empty(CharSequence chars) {
        return chars == null || chars.length() < 1;
    }

    /**
     * 检查指定的字符序是否不为 {@code null} 且包含字符。
     *
     * @param chars 表示待检查的字符序的 {@link CharSequence}。
     * @return 若字符序不为 {@code null} 且包含任何字符，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean notEmpty(CharSequence chars) {
        return !empty(chars);
    }

    /**
     * 检查指定的字符序是否是空白的字符序。
     * <p>若字符序为 {@code null}，或其只包含{@link Character#isWhitespace(char) 空白字符}，则其为一个空白字符序。</p>
     *
     * @param chars 表示待检查的字符序的 {@link CharSequence}。
     * @return 若字符序为 {@code null} 或不包含空白字符以外的其他字符，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean blank(CharSequence chars) {
        if (chars != null) {
            for (int i = 0; i < chars.length(); i++) {
                if (!Character.isWhitespace(chars.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检查指定的字符序是否不为空白的字符序。
     * <p>若字符序为 {@code null}，或其只包含{@link Character#isWhitespace(char) 空白字符}，则其为一个空白字符序。</p>
     *
     * @param chars 表示待检查的字符序的 {@link CharSequence}。
     * @return 若字符序不为 {@code null} 且包含空白字符以外的其他字符，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean notBlank(CharSequence chars) {
        return !blank(chars);
    }

    /**
     * 检查指定的字符序中是否包含指定的字符。
     *
     * @param chars 表示待检查的字符序的 {@link CharSequence}。
     * @param ch 表示待检查字符序中是否包含的字符。
     * @return 若字符序中包含指定字符，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean contains(CharSequence chars, char ch) {
        for (int i = 0; i < chars.length(); i++) {
            if (chars.charAt(i) == ch) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查两个字符串在忽略大小写的情况下是否包含相同的值。
     *
     * @param s1 表示待检查的第一个字符串的 {@link String}。
     * @param s2 表示待检查的第二个字符串的 {@link String}。
     * @return 若两个字符串包含相同的值，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean equalsIgnoreCase(String s1, String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    /**
     * 检查指定字符串在忽略大小写的情况下是否以指定后缀结尾。
     *
     * @param content 表示待检查的字符串的 {@link String}。
     * @param suffix 表示待检查的字符串后缀的 {@link String}。
     * @return 若以该后缀结尾，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean endsWithIgnoreCase(CharSequence content, CharSequence suffix) {
        if (content == null) {
            return suffix == null;
        } else if (suffix == null || content.length() < suffix.length()) {
            return false;
        } else {
            for (int i = suffix.length() - 1, j = content.length() - 1; i >= 0; i--, j--) {
                if (Character.toLowerCase(suffix.charAt(i)) != Character.toLowerCase(content.charAt(j))) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 使用指定的分隔符将指定输入对象的字符串表现形式进行拼接。
     *
     * @param separator 表示分割字符。
     * @param arguments 表示待拼接的对象的数组的 {@link Object}{@code []}。
     * @return 表示拼接后的字符串的 {@link String}。
     */
    public static String join(char separator, Object... arguments) {
        return join(Character.toString(separator), arguments);
    }

    /**
     * 使用指定的分隔符将指定输入对象的字符串表现形式进行拼接。
     *
     * @param separator 表示分割字符。
     * @param arguments 表示待拼接的对象的列表的 {@link Collection}。
     * @return 表示拼接后的字符串的 {@link String}。
     */
    public static String join(char separator, Collection<?> arguments) {
        return join(Character.toString(separator), arguments);
    }

    /**
     * 使用指定的分隔符将指定输入对象按指定方法转为字符串表现形式后进行拼接。
     *
     * @param separator 表示分割字符。
     * @param primaries 表示待拼接的对象的列表的 {@link Collection}。
     * @param mapper 表示将对象映射成为字符串表现形式的方法的 {@link Function}。
     * @return 表示拼接后的字符串的 {@link String}。
     */
    public static <T> String join(char separator, Collection<T> primaries, Function<T, String> mapper) {
        return join(Character.toString(separator), primaries, mapper);
    }

    /**
     * 使用指定的分隔符将指定输入对象的字符串表现形式进行拼接。
     *
     * @param separator 表示分割符的 {@link String}。
     * @param arguments 表示待拼接的对象的数组的 {@link Object}{@code []}。
     * @return 表示拼接后的字符串的 {@link String}。
     */
    public static String join(String separator, Object... arguments) {
        return join(separator, ObjectUtils.<Object[], List<Object>>mapIf(arguments, Arrays::asList));
    }

    /**
     * 使用指定的分隔符将指定输入对象的字符串表现形式进行拼接。
     *
     * @param separator 表示分割符的 {@link String}。
     * @param arguments 表示待拼接的对象的列表的 {@link Collection}。
     * @return 表示拼接后的字符串的 {@link String}。
     */
    public static String join(String separator, Collection<?> arguments) {
        return join(separator, arguments, null);
    }

    /**
     * 使用指定的分隔符将指定输入对象按指定方法转为字符串表现形式后进行拼接。
     *
     * @param separator 表示分割符的 {@link String}。
     * @param primaries 表示待拼接的对象的列表的 {@link Collection}。
     * @param mapper 表示将对象映射成为字符串表现形式的方法的 {@link Function}。
     * @return 表示拼接后的字符串的 {@link String}。
     */
    public static <T> String join(String separator, Collection<T> primaries, Function<T, String> mapper) {
        if (primaries == null || primaries.isEmpty()) {
            return EMPTY;
        } else {
            Function<T, String> actualMapper = ObjectUtils.nullIf(mapper, () -> StringUtils::toString);
            Iterator<T> iterator = primaries.iterator();
            StringBuilder builder = new StringBuilder();
            builder.append(actualMapper.apply(iterator.next()));
            while (iterator.hasNext()) {
                builder.append(separator).append(actualMapper.apply(iterator.next()));
            }
            return builder.toString();
        }
    }

    /**
     * 检查指定的字符序是否可表示数字。
     *
     * @param content 表示待检查的字符序的 {@link CharSequence}。
     * @param signed 若为 {@code true}，则允许是有符号数字；否则只能是无符号数字。
     * @param decimal 若为 {@code true}，则允许是小数；否则只能是整数。
     * @return 若字符序的内容可表示数字，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean numeric(CharSequence content, boolean signed, boolean decimal) {
        if (content == null) {
            return false;
        } else {
            return numeric(content, 0, content.length(), signed, decimal);
        }
    }

    /**
     * 检查指定的字符序中的特定部分是否可表示数字。
     *
     * @param content 表示待检查的字符序的 {@link CharSequence}。
     * @param start 表示待检查的内容在字符序中的开始位置的32位整数。开始位置将被检查。
     * @param end 表示待检查的内容在字符序中的结束位置的32位整数。结束位置不会被检查。
     * @param signed 若为 {@code true}，则允许是有符号数字；否则只能是无符号数字。
     * @param decimal 若为 {@code true}，则允许是小数；否则只能是整数。
     * @return 若字符序的内容可表示数字，则为 {@code true}；否则为 {@code false}。
     * @throws IllegalArgumentException {@code start} 和 {@code end} 不能表示字符序的子序列。
     */
    public static boolean numeric(CharSequence content, int start, int end, boolean signed, boolean decimal) {
        if (content == null) {
            return false;
        } else if (start < 0) {
            throw new IllegalArgumentException(String.format(Locale.ROOT,
                    "The start index cannot be negative. [start=%d]", start));
        } else if (end > content.length()) {
            throw new IllegalArgumentException(String.format(Locale.ROOT,
                    "The end index is out of range. [end=%d, content.length=%d]",
                    end, content.length()));
        } else if (end < start) {
            throw new IllegalArgumentException(String.format(Locale.ROOT,
                    "The end index is less than the start index. [start=%d, end=%d]",
                    start, end));
        } else if (end == start) {
            return false;
        } else if (signed && CharacterUtils.in(content.charAt(0), '+', '-')) {
            return numeric(content, start + 1, end, false, decimal);
        } else {
            boolean numeric = false;
            while (start < end) {
                char ch = content.charAt(start++);
                if (ch >= '0' && ch <= '9') {
                    numeric = true;
                } else if (ch == '.' && decimal) {
                    decimal = false;
                } else {
                    return false;
                }
            }
            return numeric;
        }
    }

    /**
     * 将指定的字符串转为大写。
     *
     * @param value 表示原始字符串的 {@link String}。
     * @return 表示转为大写的字符串的 {@link String}。
     */
    public static String upper(String value) {
        if (value != null) {
            value = value.toUpperCase(Locale.ROOT);
        }
        return value;
    }

    /**
     * 将指定的字符串转为小写。
     *
     * @param value 表示原始字符串的 {@link String}。
     * @return 表示转为小写的字符串的 {@link String}。
     */
    public static String lower(String value) {
        if (value != null) {
            value = value.toLowerCase(Locale.ROOT);
        }
        return value;
    }

    /**
     * 当值为空白字符串时，使用默认值；否则使用原始值。
     *
     * @param value 表示原始值的 {@link String}。
     * @param defaultValue 表示默认值的 {@link String}。
     * @return 若原始值是空白字符串，则使用默认值；否则使用原始值。
     */
    public static String blankIf(String value, String defaultValue) {
        if (blank(value)) {
            return defaultValue;
        } else {
            return value;
        }
    }

    /**
     * 当值为空白字符串时，使用默认值；否则使用原始值。
     *
     * @param value 表示原始值的 {@link String}。
     * @param defaultValueSupplier 表示默认值的提供程序的 {@link Supplier}。
     * @return 若原始值是空白字符串，则使用默认值；否则使用原始值。
     */
    public static String blankIf(String value, Supplier<String> defaultValueSupplier) {
        if (blank(value)) {
            if (defaultValueSupplier == null) {
                throw new IllegalArgumentException("The supplier of default value cannot be null.");
            } else {
                return defaultValueSupplier.get();
            }
        } else {
            return value;
        }
    }

    /**
     * 将指定的字符串按照指定的分隔符进行分割。
     *
     * @param separator 表示分隔字符。
     * @param value 表示待分割的字符串的 {@link String}。
     * @return 表示分割后得到的子串的列表的 {@link List}{@code <}{@link String}{@code >}。
     */
    public static List<String> split(char separator, String value) {
        return split(value, separator, false);
    }

    /**
     * 将指定的字符串按照指定的分隔符进行分割。
     *
     * @param value 表示待分割的字符串的 {@link String}。
     * @param separator 表示分隔字符。
     * @param ignoreEmptyEntries 若为 {@code true}，则结果中不包含空子串；否则包含空子串。
     * @return 表示分割后得到的子串的列表的 {@link List}{@code <}{@link String}{@code >}。
     */
    public static List<String> split(String value, char separator, boolean ignoreEmptyEntries) {
        if (value == null) {
            return Collections.emptyList();
        } else {
            List<String> parts = new ArrayList<>();
            int start = 0;
            for (int i = 0, length = value.length(); i < length; i++) {
                if (value.charAt(i) == separator) {
                    if (i > start || !ignoreEmptyEntries) {
                        parts.add(value.substring(start, i));
                    }
                    start = i + 1;
                }
            }
            if (start < value.length() || !ignoreEmptyEntries) {
                parts.add(value.substring(start));
            }
            return Collections.unmodifiableList(parts);
        }
    }

    /**
     * 检查指定字符串的长度是否在指定的有效区间内。
     *
     * @param value 表示待检查长度的字符串的 {@link String}。
     * @param minimum 表示字符串有效长度的最小值的32位整数。最小值在有效区间内。
     * @param maximum 表示字符串有效长度的最大值的32位整数。最大值在有效区间内。
     * @return 若字符串的长度在有效区间内，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean lengthBetween(String value, int minimum, int maximum) {
        return value != null && value.length() >= minimum && value.length() <= maximum;
    }

    /**
     * 检查指定字符串的长度是否不在指定的有效区间内。
     *
     * @param value 表示待检查长度的字符串的 {@link String}。
     * @param minimum 表示字符串有效长度的最小值的32位整数。最小值在有效区间内。
     * @param maximum 表示字符串有效长度的最大值的32位整数。最大值在有效区间内。
     * @return 若字符串的长度不在有效区间内，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean lengthNotBetween(String value, int minimum, int maximum) {
        return !lengthBetween(value, minimum, maximum);
    }

    /**
     * 返回一个数组，包含字符序中的所有字符。
     *
     * @param sequence 表示待转为数组的字符序的 {@link CharSequence}。
     * @return 表示包含字符序中所有字符的数组的 {@code char[]}。
     */
    public static char[] chars(CharSequence sequence) {
        if (sequence == null) {
            return null;
        } else {
            char[] chars = new char[sequence.length()];
            for (int i = 0, length = chars.length; i < length; i++) {
                chars[i] = sequence.charAt(i);
            }
            return chars;
        }
    }

    /**
     * 剪裁指定字符串前后的空白字符。
     *
     * @param value 表示待剪裁的原始字符串的 {@link String}。
     * @return 表示剪裁后的字符串的 {@link String}。
     */
    public static String trim(String value) {
        if (notEmpty(value)) {
            int start = 0;
            int end = value.length() - 1;
            while (start < value.length() && Character.isWhitespace(value.charAt(start))) {
                start++;
            }
            while (end > start && Character.isWhitespace(value.charAt(end))) {
                end--;
            }
            return value.substring(start, end + 1);
        } else {
            return value;
        }
    }

    /**
     * 剪裁掉字符串开始处的所有空白字符。
     *
     * @param value 表示待剪裁的字符串的 {@link String}。
     * @return 表示剪裁后的字符串的 {@link String}。
     */
    public static String trimStart(String value) {
        if (notEmpty(value)) {
            int index = 0;
            while (index < value.length() && Character.isWhitespace(value.charAt(index))) {
                index++;
            }
            if (index == value.length()) {
                return EMPTY;
            } else {
                return value.substring(index);
            }
        } else {
            return value;
        }
    }

    /**
     * 检查指定值在忽略大小写的情况下是否在目标可选值之中。
     *
     * @param value 表示待检查的值的 {@link String}。
     * @param availableValues 表示可选的值的 {@link String}{@code []}。
     * @return 若在可选值中，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean inIgnoreCase(String value, String... availableValues) {
        if (availableValues != null && availableValues.length > 0) {
            for (String available : availableValues) {
                if (StringUtils.equalsIgnoreCase(value, available)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查字符序中的内容是否都是十六进制字符。
     *
     * @param chars 表示待检查的字符序的 {@link CharSequence}。
     * @return 若都是十六进制字符，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean hex(CharSequence chars) {
        return hex(chars, 0, chars.length());
    }

    /**
     * 检查字符序中从指定位置开始后的指定长度的内容是否都是十六进制字符。
     *
     * @param chars 表示待检查的字符序的 {@link CharSequence}。
     * @param start 表示待检查的子序的开始位置的32位整数。
     * @param length 表示待检查的子序的长度的32位整数。
     * @return 若都是十六进制字符，则为 {@code true}；否则为 {@code false}。
     */
    public static boolean hex(CharSequence chars, int start, int length) {
        return IntStream.range(start, Math.min(chars.length(), start + length))
                .allMatch(index -> CharacterUtils.hex(chars.charAt(index)));
    }

    /**
     * 将指定的字节序转为十六进制字符串。
     *
     * @param bytes 表示待转为十六进制字符串的字节序的 {@code byte[]}。
     * @return 表示包含字节序数据的十六进制字符串的 {@link String}。
     */
    public static String toHexString(byte[] bytes) {
        Validation.notNull(bytes, "The bytes to convert hex string cannot be null.");
        StringBuilder builder = new StringBuilder(bytes.length << 1);
        for (byte value : bytes) {
            int intValue = Byte.toUnsignedInt(value);
            builder.append(hex((intValue >> 4) & 0xf));
            builder.append(hex(intValue & 0xf));
        }
        return builder.toString();
    }

    private static char hex(int value) {
        if (value < 10) {
            return (char) ('0' + value);
        } else {
            return (char) ('a' + value - 10);
        }
    }
}
