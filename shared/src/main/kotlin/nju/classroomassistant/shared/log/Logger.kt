package nju.classroomassistant.shared.log

enum class LogSeverity {
    VERBOSE, WARNING, ERROR
}

interface Logger {
    val loggerTag: String
        get() = javaClass.simpleName

    fun log(message: String, severity: LogSeverity) {
        println("[$loggerTag] [$severity] $message")
    }

    fun verbose(message: String) {
        log(message, LogSeverity.VERBOSE)
    }

    fun warning(message: String) {
        log(message, LogSeverity.WARNING)
    }

    fun error(message: String) {
        log(message, LogSeverity.ERROR)
    }
}


