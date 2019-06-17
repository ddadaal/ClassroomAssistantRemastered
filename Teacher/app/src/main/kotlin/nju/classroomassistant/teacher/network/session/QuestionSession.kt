package nju.classroomassistant.teacher.network.session

import javafx.beans.property.SimpleBooleanProperty
import javafx.collections.FXCollections
import nju.classroomassistant.shared.log.Logger
import nju.classroomassistant.shared.messages.raisequestion.NotificationSettingChangeMessage
import nju.classroomassistant.teacher.models.StudentInfo
import nju.classroomassistant.teacher.network.GlobalVariables
import nju.classroomassistant.teacher.network.MOCK
import nju.classroomassistant.teacher.network.Server
import nju.classroomassistant.teacher.network.StudentItem
import nju.classroomassistant.teacher.repository.TeacherInfoRepository
import nju.classroomassistant.teacher.views.question.DialogFragment
import nju.classroomassistant.teacher.views.question.NotificationDialog
import tornadofx.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

/**
 * Author: J.D. Liao
 * Date: 2019-06-12
 * Description:
 */
class QuestionSession: Logger {

    /**
     * To indicate whether the notification is open
     */
    var isNotificationOpen = SimpleBooleanProperty(false).apply {
        addListener { _, oldValue, newValue ->
            if (oldValue != newValue) {

                verbose("Is notification changed to $newValue")

                Server.writeToAllStudentsAsync(NotificationSettingChangeMessage(newValue))
                GlobalVariables.teacherInfo.get().questionNotification = newValue
                TeacherInfoRepository.save()
            }
        }
    }

    /**
     * Questions' list
     */
    val questionList = FXCollections.observableArrayList<QuestionItem>()!!.apply {
        if (MOCK) {
            for (i in 1..30)
                add(QuestionItem("Test content $i", "Nickname"))
        }

    }

    fun addQuestion(content: String, studentId: String) {

        Server.studentMap.getStudentById(studentId)?.let {
            runLater {
                val questionItem = QuestionItem(content, it.nickname)
                questionList.add(0, questionItem)
                if (isNotificationOpen.get()) {
                    openNotificationDialog(questionItem)
                }
            }
        }

    }

    var openNotificationDialog = { _: QuestionItem -> }

}

const val ABSTRACT_LENGTH = 15

data class QuestionItem(
        val content: String,
        val studentNickname: String,
        val time: LocalDateTime = LocalDateTime.now()
) {
    val abstract
        get() = if (content.length > ABSTRACT_LENGTH) {
            content.substring(0..ABSTRACT_LENGTH) + "..."
        } else {
            content
        }

    val simpleTime
        get() = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"))!!
}