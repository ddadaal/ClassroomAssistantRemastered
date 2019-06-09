package nju.classroomassistant.shared.messages.discussion

import nju.classroomassistant.shared.messages.Message

// 学生端发给服务器的，表示发送了一个讨论

class StudentSendDiscussionMessage(
    val content: String
): Message()