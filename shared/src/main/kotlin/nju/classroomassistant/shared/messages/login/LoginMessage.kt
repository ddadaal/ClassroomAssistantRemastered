package nju.classroomassistant.shared.messages.login

import nju.classroomassistant.shared.messages.Message
import java.io.Serializable

class LoginMessage(
    val studentId: String
): Message()

enum class LoginResponse: Serializable {
    INITIAL_LOGIN, // 初次登录
    RECONNECT, // 是用一台设备重新连接的，就是之前登录过
    RECONNECT_DIFFERENT_DEVICE, // 用另一端设备重新连接的
    NOT_ALLOWED, // 没有选这个课
    ERROR // 出错了
}

class LoginResponseMessage(
    val response: LoginResponse
): Message()