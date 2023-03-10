package edu.kit.informatik.io;

public class ErrorLogger {

    private final static String ERROR_FORMAT = "Error: %s";

    public static String format(String string) {
        return String.format(ERROR_FORMAT, string);
    }
}
