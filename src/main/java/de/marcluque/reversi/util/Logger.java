package de.marcluque.reversi.util;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * Created with <3 by marcluque, March 2021
 */
public class Logger {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");;

    private static void message(PrintStream stream, String message) {
        stream.println(message);
    }

    public static void print(String message) {
        message(System.out, "[%s]: %s".formatted(dateTimeFormatter.format(LocalDateTime.now()), message));
    }

    public static void print(String formatMessage, Object... args) {
        message(System.out, "[%s]: %s".formatted(dateTimeFormatter.format(LocalDateTime.now()), formatMessage.formatted(args)));
    }

    public static void error(String message) {
        message(System.err, "[%s]: %s".formatted(dateTimeFormatter.format(LocalDateTime.now()), message));
    }

    public static void error(String formatMessage, Object... args) {
        message(System.err, "[%s]: %s".formatted(dateTimeFormatter.format(LocalDateTime.now()), formatMessage.formatted(args)));
    }
}