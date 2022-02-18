package xyz.talefox.util.support;

import xyz.talefox.util.StringUtils;
import xyz.talefox.util.Validation;
import xyz.talefox.util.Version;
import xyz.talefox.util.VersionFormatException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * 为版本提供定义。
 *
 * @author 梁济时
 * @since 2021/10/2
 */
public class DefaultVersion implements Version {
    /**
     * 为版本提供默认的比较程序。
     */
    public static final Comparator<Version> COMPARATOR = Comparator.comparingInt(Version::major)
            .thenComparingInt(Version::minor)
            .thenComparingInt(Version::revision)
            .thenComparingInt(Version::build);

    private final int major;
    private final int minor;
    private final int revision;
    private final int build;

    /**
     * 使用主版本号、次版本号、修订版本号和构建版本号初始化 {@link DefaultVersion} 类的新实例。
     *
     * @param major 表示主版本号的32位整数。
     * @param minor 表示次版本号的32位整数。
     * @param revision 表示修订版本号的32位整数。
     * @param build 表示构建版本号的32位整数。
     * @throws IllegalArgumentException {@code major}、{@code minor}、{@code revision} 或 {@code build} 为负数。
     */
    public DefaultVersion(int major, int minor, int revision, int build) {
        this.major = Validation.greaterThanOrEquals(major, 0, "The major of a version cannot be negative.");
        this.minor = Validation.greaterThanOrEquals(minor, 0, "The minor of a version cannot be negative.");
        this.revision = Validation.greaterThanOrEquals(revision, 0, "The revision of a version cannot be negative.");
        this.build = Validation.greaterThanOrEquals(build, 0, "The build of a version cannot be negative.");
    }

    @Override
    public int major() {
        return this.major;
    }

    @Override
    public int minor() {
        return this.minor;
    }

    @Override
    public int revision() {
        return this.revision;
    }

    @Override
    public int build() {
        return this.build;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.major()).append(SEPARATOR).append(this.minor());
        if (this.build() > 0) {
            builder.append(SEPARATOR).append(this.revision()).append(SEPARATOR).append(this.build());
        } else if (this.revision() > 0) {
            builder.append(SEPARATOR).append(this.revision());
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Version) {
            Version another = (Version) obj;
            return another.major() == this.major() && another.minor() == this.minor()
                    && another.revision() == this.revision() && another.build() == this.build();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[] { this.major(), this.minor(), this.revision(), this.build() });
    }

    /**
     * 从字符串中解析版本号默认实现的信息。
     *
     * @param s 表示包含版本号信息的字符串的 {@link String}。
     * @return 表示从字符串中解析到的版本号的信息的 {@link DefaultVersion}。
     * @throws VersionFormatException 字符串中包含的版本号信息的格式不正确。
     */
    public static DefaultVersion parse(String s) {
        if (StringUtils.blank(s)) {
            return null;
        } else {
            List<String> parts = StringUtils.split('.', s);
            if (parts.size() < 2) {
                throw new VersionFormatException("A version must have major and minor at least.");
            } else if (parts.size() > 4) {
                throw new VersionFormatException(String.format(Locale.ROOT,
                        "A version can only contain 4 parts at most. [actual=%d]", parts.size()));
            } else {
                return new DefaultVersion(
                        parse(parts, 0, "major"),
                        parse(parts, 1, "minor"),
                        parse(parts, 2, "revision"),
                        parse(parts, 3, "build"));
            }
        }
    }

    private static int parse(List<String> parts, int index, String part) {
        if (index < parts.size()) {
            try {
                return Integer.parseUnsignedInt(parts.get(index));
            } catch (NumberFormatException ex) {
                throw new VersionFormatException(String.format(Locale.ROOT,
                        "The %s version must be a non-negative integer. [actual=%s]",
                        part, parts.get(index)), ex);
            }
        } else {
            return 0;
        }
    }
}
