package nju.classroomassistant.teacher.network

import nju.classroomassistant.shared.messages.raisequestion.NotificationSettingChangeMessage
import tornadofx.*
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

/**
 * Author: J.D. Liao
 * Date: 2019-06-12
 * Description:
 */
object QuestionSession {
    var isNotificationOpen: Boolean by Delegates.observable(false) { _, _, open ->
        // Sending a change notice to clients
        Thread {
            for (s in Server.studentMap.allStudents) {
                s.handler.writeMessage(NotificationSettingChangeMessage(open))
            }
        }.start()
    }

    val questionList = ArrayList<QuestionItem>().apply {
        addAll(
            Collections.nCopies(
                30,
                QuestionItem("Test content", "StudentTest", LocalDateTime.now())
            )
        )
    }.observable()
}

data class QuestionItem(
    val content: String,
    val studentId: String?,
    val time: LocalDateTime,
    var isVisited: Boolean = false
)