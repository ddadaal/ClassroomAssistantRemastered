package nju.classroomassistant.teacher.util

import tornadofx.*
import java.util.*

fun executeLater(ms: Long, op: () -> Unit) {

    Thread {
        Thread.sleep(ms)
        runLater {
            op()
        }
    }.start()

}