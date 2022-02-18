package xyz.talefox.util;

public class VersionFormatException extends IllegalArgumentException {
    public VersionFormatException(String message) {
        super(message);
    }

    public VersionFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
