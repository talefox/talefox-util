package xyz.talefox.util;

import xyz.talefox.util.support.DefaultVersion;

/**
 * 为版本提供定义。
 *
 * @author 梁济时
 * @since 2021/10/2
 */
public interface Version extends Comparable<Version> {
    /**
     * 表示版本号间的分隔符。
     */
    char SEPARATOR = '.';

    /**
     * 获取主版本号。
     * <p>通常当架构、功能等发生重要改变时增长。</p>
     *
     * @return 表示主版本号的32位整数。
     */
    int major();

    /**
     * 获取次版本号。
     * <p>通常发布新版本时增长。</p>
     *
     * @return 表示次版本号的32位整数。
     */
    int minor();

    /**
     * 获取修订版本号。
     * <p>常规版本发布前，对上个版本进行修订时正常。</p>
     *
     * @return 表示修订版本号的32位整数。
     */
    int revision();

    /**
     * 获取构建版本号。
     * <p>每次构建时正常。</p>
     *
     * @return 表示构建版本号的32位整数。
     */
    int build();

    /**
     * 将当前版本与另一个版本进行比较。
     *
     * @param another 表示待与当前版本进行比较的另一个版本的 {@link Version}。
     * @return 表示待与当前版本比较的另一个版本的 {@link Version}。
     * @throws IllegalArgumentException {@code another} 为 {@code null}。
     */
    default int compareTo(Version another) {
        Validation.notNull(another, "The version to compare to cannot be null.");
        return DefaultVersion.COMPARATOR.compare(this, another);
    }

    /**
     * 使用主版本号和次版本号创建版本号的新实例。
     *
     * @param major 表示主版本号的32位整数。
     * @param minor 表示次版本号的32位整数。
     * @return 表示新创建的版本号实例的 {@link Version}。
     * @throws IllegalArgumentException {@code major} 或 {@code minor} 为负数。
     */
    static Version create(int major, int minor) {
        return create(major, minor, 0, 0);
    }

    /**
     * 使用主版本号、次版本号和修订版本号创建版本号的新实例。
     *
     * @param major 表示主版本号的32位整数。
     * @param minor 表示次版本号的32位整数。
     * @param revision 表示修订版本号的32位整数。
     * @return 表示新创建的版本号实例的 {@link Version}。
     * @throws IllegalArgumentException {@code major}、{@code minor} 或 {@code revision} 为负数。
     */
    static Version create(int major, int minor, int revision) {
        return create(major, minor, revision, 0);
    }

    /**
     * 使用主版本号、次版本号、修订版本号和构建版本号创建版本号的新实例。
     *
     * @param major 表示主版本号的32位整数。
     * @param minor 表示次版本号的32位整数。
     * @param revision 表示修订版本号的32位整数。
     * @param build 表示构建版本号的32位整数。
     * @return 表示新创建的版本号实例的 {@link Version}。
     * @throws IllegalArgumentException {@code major}、{@code minor}、{@code revision} 或 {@code build} 为负数。
     */
    static Version create(int major, int minor, int revision, int build) {
        return new DefaultVersion(major, minor, revision, build);
    }

    /**
     * 从字符串中解析版本号默认实现的信息。
     *
     * @param s 表示包含版本号信息的字符串的 {@link String}。
     * @return 表示从字符串中解析到的版本号的信息的 {@link Version}。
     * @throws VersionFormatException 字符串中包含的版本号信息的格式不正确。
     */
    static Version parse(String s) {
        return DefaultVersion.parse(s);
    }
}
