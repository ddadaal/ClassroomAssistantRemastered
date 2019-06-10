package nju.classroomassistant.shared.messages.login

import nju.classroomassistant.shared.messages.Message
import java.io.Serializable

class LoginMessage(
    val studentId: String
): Message()

enum class LoginResponse: Serializable {
    OK,
    NOT_ALLOWED, // 没有选这个课
    ERROR // 出错了
}

class LoginResponseMessage(
    val response: LoginResponse
): Message()