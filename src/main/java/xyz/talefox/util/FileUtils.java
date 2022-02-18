package xyz.talefox.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

/**
 * 为文件提供工具方法。
 *
 * @author 梁济时
 * @since 2021/9/27
 */
public final class FileUtils {
    /** 隐藏默认构造方法，避免工具类被实例化。 */
    private FileUtils() {}

    /**
     * 返回标准化的文件的路径。
     *
     * @param file 表示待获取标准化路径的文件的 {@link File}。
     * @return 若 {@code file} 为 {@code null}，则为 {@code null}；否则为文件的标准化路径的 {@link String}。
     * @throws IllegalStateException 获取标准化路径失败。
     */
    public static String path(File file) {
        if (file == null) {
            return null;
        } else {
            try {
                return file.getCanonicalPath();
            } catch (IOException ex) {
                throw new IllegalStateException("Failed to fetch canonical path of file.", ex);
            }
        }
    }

    /**
     * 返回标准化后的文件。
     *
     * @param file 表示原文件的 {@link File}。
     * @return 若原文件为 {@code null}，则为 {@code null}；否则为表示标准化后的文件的 {@link File}。
     * @throws IllegalStateException 标准化文件失败。
     */
    public static File canonical(File file) {
        File canonical = file;
        if (canonical != null) {
            try {
                canonical = canonical.getCanonicalFile();
            } catch (IOException ex) {
                throw new IllegalStateException("Failed to canonical file.", ex);
            }
        }
        return canonical;
    }

    /**
     * 将文件转为 URL。
     *
     * @param file 表示待转为 URL 的文件的 {@link File}。
     * @return 表示文件的 URL 的 {@link URL}。
     * @throws IllegalStateException 转换为 URL 的过程发生格式性错误。
     */
    public static URL toUrl(File file) {
        if (file == null) {
            return null;
        } else {
            try {
                return file.toURI().toURL();
            } catch (MalformedURLException ex) {
                throw new IllegalStateException(String.format(Locale.ROOT,
                        "Failed to convert file to URL. [file=%s]", path(file)), ex);
            }
        }
    }
}
