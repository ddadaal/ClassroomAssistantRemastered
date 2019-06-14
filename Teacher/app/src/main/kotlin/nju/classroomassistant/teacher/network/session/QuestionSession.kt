package nju.classroomassistant.teacher.network.session

import javafx.beans.property.SimpleBooleanProperty
import nju.classroomassistant.shared.messages.raisequestion.NotificationSettingChangeMessage
import nju.classroomassistant.teacher.models.StudentInfo
import nju.classroomassistant.teacher.network.Server
import tornadofx.*
import java.time.LocalDateTime
import kotlin.collections.ArrayList

/**
 * Author: J.D. Liao
 * Date: 2019-06-12
 * Description:
 */
class QuestionSession {

    /**
     * To indicate whether the notification is open
     */
    var isNotificationOpen = SimpleBooleanProperty(false).apply {
        addListener { _, _, _ ->
            toggle()
        }
    }

    /**
     * Questions' list
     */
    val questionList = ArrayList<QuestionItem>().apply {
        for (i in 1..30)
            add(QuestionItem("Test content $i", "Student $i"))
    }.observable()

    fun addQuestion(content: String, studentId: String) {
        runLater {
            questionList.add(QuestionItem(content, studentId))
        }
    }

    /**
     * Send NotificationSettingChangeMessage to students' clients
     */
    private fun toggle() {
        Server.writeToAllStudentsAsync(NotificationSettingChangeMessage(isNotificationOpen.get()))
    }
}

data class QuestionItem(
    val content: String,
    val student: String?,
    val time: LocalDateTime = LocalDateTime.now()
)