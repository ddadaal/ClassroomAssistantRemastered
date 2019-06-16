package nju.classroomassistant.teacher.network.session

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import nju.classroomassistant.shared.messages.discussion.DiscussionEndMessage
import nju.classroomassistant.shared.messages.discussion.DiscussionStartMessage
import nju.classroomassistant.shared.messages.discussion.StudentSendDiscussionMessage
import nju.classroomassistant.teacher.network.Server
import nju.classroomassistant.teacher.network.StudentItem
import tornadofx.*
import kotlin.collections.ArrayList

class DiscussionSession {
    var discussionCount = 0
    var started = SimpleBooleanProperty(false)

    var title = SimpleStringProperty("点击开始按钮开始一个讨论")

    var discussionItems = ArrayList<DiscussionItem>().observable()

    fun start() {
        started.set(true)
        title.set("讨论${discussionCount++}")
        Server.writeToAllStudentsAsync(DiscussionStartMessage())
    }

    fun stop() {
        started.set(false)
        discussionItems.clear()
        Server.writeToAllStudentsAsync(DiscussionEndMessage())
    }

    fun toggle() {
        if (started.get()) {
            stop()
        } else {
            start()
        }
    }

    fun add(studentItem: StudentItem, message: StudentSendDiscussionMessage) {
        runLater {
            discussionItems.add(DiscussionItem(message, studentItem.studentId))
        }
    }
}

class DiscussionItem(message: StudentSendDiscussionMessage, val nickname: String?) {
    val content = message.content

    override fun toString(): String {
        return "[$nickname] $content"
    }
}