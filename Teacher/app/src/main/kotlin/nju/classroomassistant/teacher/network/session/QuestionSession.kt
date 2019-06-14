package nju.classroomassistant.teacher.network.session

import javafx.beans.property.SimpleBooleanProperty
import nju.classroomassistant.shared.messages.raisequestion.NotificationSettingChangeMessage
import nju.classroomassistant.teacher.network.Server
import nju.classroomassistant.teacher.network.StudentInfo
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

    fun add(student: StudentInfo, question: String) {
        questionList.add(QuestionItem(student.studentId, question))
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
    val studentId: String?,
    val time: LocalDateTime = LocalDateTime.now()
)