package nju.classroomassistant.teacher.network.session

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import nju.classroomassistant.shared.messages.discussion.DiscussionStartMessage
import nju.classroomassistant.shared.messages.discussion.StudentSendDiscussionMessage
import nju.classroomassistant.teacher.network.GlobalVariables
import nju.classroomassistant.teacher.network.Server
import nju.classroomassistant.teacher.network.StudentInfo
import tornadofx.*
import java.util.*
import kotlin.collections.ArrayList

class DiscussionSession {
    var discussionCount = 0
    var started = SimpleBooleanProperty(false)

    var title = SimpleStringProperty("点击开始按钮开始一个讨论")

    var discussionItems = ArrayList<DiscussionItem>().apply {
        addAll(Collections.nCopies(30, DiscussionItem(StudentSendDiscussionMessage("ok\nok"), "12")))
    }.observable()

    fun start() {
        started.set(true)
        title.set("讨论${discussionCount++}")
        Server.writeToAllStudentsAsync(DiscussionStartMessage())
    }

    fun stop() {
        started.set(false)
        discussionItems.clear()
    }

    fun toggle() {
        if (started.get()) {
            stop()
        } else {
            start()
        }
    }

    fun add(studentInfo: StudentInfo, message: StudentSendDiscussionMessage) {
        discussionItems.add(DiscussionItem(message, studentInfo.studentId))
    }
}

class DiscussionItem(message: StudentSendDiscussionMessage, private val nickname: String?) {
    private val content = message.content

    override fun toString(): String {
        return "[$nickname] $content"
    }
}