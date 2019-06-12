package nju.classroomassistant.teacher.network.session

import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.NodeOrientation
import nju.classroomassistant.shared.messages.raisequestion.NotificationSettingChangeMessage
import nju.classroomassistant.teacher.network.Server
import tornadofx.*
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

/**
 * Author: J.D. Liao
 * Date: 2019-06-12
 * Description:
 */
class QuestionSession {

    var isNotificationOpen = SimpleBooleanProperty(false)

    val questionList = ArrayList<QuestionItem>().apply {
        addAll(
            Collections.nCopies(
                30,
                QuestionItem(
                    "Test content",
                    "StudentTest",
                    LocalDateTime.now()
                )
            )
        )
    }.observable()

    fun toggle() {
        if (isNotificationOpen.get()) {
            close()
        } else {
            open()
        }
    }

    private fun open() {
        isNotificationOpen.set(true)
        Server.writeToAllStudentsAsync(NotificationSettingChangeMessage(true))
    }

    private fun close() {
        isNotificationOpen.set(false)
        Server.writeToAllStudentsAsync(NotificationSettingChangeMessage(false))
    }
}

data class QuestionItem(
    val content: String,
    val studentId: String?,
    val time: LocalDateTime,
    var isVisited: Boolean = false
)