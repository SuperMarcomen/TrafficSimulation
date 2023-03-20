package edu.kit.informatik.io;

/**
 * Formats error messages with a prefix.
 *
 * @author uswry
 * @version 1.0
 */
public final class ErrorLogger {

    private static final String ERROR_FORMAT = "Error: %s";

    private ErrorLogger() {

    }

    /**
     * Formats the string to look like an error.
     *
     * @param string - The string to be formatted
     * @return the formatted error string
     */
    public static String format(String string) {
        return String.format(ERROR_FORMAT, string);
    }
}
