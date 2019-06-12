package nju.classroomassistant.teacher.network

import nju.classroomassistant.shared.messages.discussion.DiscussionEndMessage
import nju.classroomassistant.shared.messages.discussion.DiscussionStartMessage
import nju.classroomassistant.shared.messages.discussion.StudentSendDiscussionMessage
import nju.classroomassistant.teacher.views.discussion.DiscussionItem
import nju.classroomassistant.teacher.views.question.QuestionItem
import tornadofx.*
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

/**
 * Author: J.D. Liao
 * Date: 2019-06-10
 * Description:
 */
object GlobalVariables {

    private val DISCUSSION_INITIAL_SIZE = 30

    var discussionStart: Boolean by Delegates.observable(false) { _, _, newValue ->
        // Send ending message to student clients when quit discussion
        if (!newValue) {
            Thread { sendDiscussionEndingMessage() }.start()
        } else {
            // Start a new discussion, clear previous discussion content and increment discussionCount
            Thread { sendDiscussionStartMessage() }.start()
            currentDiscussionQueue.clear()
            discussionCount++
        }
    }

    var discussionCount = 0

    val currentDiscussionQueue = ArrayList<DiscussionItem>().apply {
        addAll(
            Collections.nCopies(
                DISCUSSION_INITIAL_SIZE,
                DiscussionItem(StudentSendDiscussionMessage("ok\nok"), "12")
            )
        )
    }.observable()

    var questionAlert = false

    val questionList = ArrayList<QuestionItem>().apply {
        addAll(
            Collections.nCopies(
                DISCUSSION_INITIAL_SIZE,
                QuestionItem("Test content", "StudentTest", LocalDateTime.now())
            )
        )
    }.observable()

    fun addDiscussionMessage(studentId: String?, message: StudentSendDiscussionMessage) {
        currentDiscussionQueue.add(DiscussionItem(message, studentId))
    }

    private fun sendDiscussionEndingMessage() {
        for (s in Server.studentMap.allStudents) {
            s.handler.writeMessage(DiscussionEndMessage())

        }
    }

    private fun sendDiscussionStartMessage() {
        for (s in Server.studentMap.allStudents) {
            s.handler.writeMessage(DiscussionStartMessage())
        }
    }
}