package nju.classroomassistant.teacher.util

import tornadofx.*
import java.util.*

fun executeLater(ms: Long, op: () -> Unit) {

    Timer().schedule(object: TimerTask() {
        override fun run() {
            runLater(op)
        }
    }, ms)

}