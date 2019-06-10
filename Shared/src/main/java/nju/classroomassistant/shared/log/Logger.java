package nju.classroomassistant.shared.log;

public interface Logger {
    default String getLoggerTag() {
        return getClass().getSimpleName();
    }

    default void log(String message, LogSeverity severity) {
        System.out.println(String.format("[%s] [%s] %s", getLoggerTag(), severity, message));
    }

    default void verbose(String message) {
        log(message, LogSeverity.VERBOSE);
    }

    default void warning(String message) {
        log(message, LogSeverity.WARNING);
    }

    default void error(String message) {
        log(message, LogSeverity.ERROR);
    }


}