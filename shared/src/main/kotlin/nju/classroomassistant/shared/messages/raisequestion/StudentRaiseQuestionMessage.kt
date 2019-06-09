package nju.classroomassistant.shared.messages.raisequestion

import nju.classroomassistant.shared.messages.Message


// 客户端到服务器，学生提问

class StudentRaiseQuestionMessage(
    val question: String
): Message()