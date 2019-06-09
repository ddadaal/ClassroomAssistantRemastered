package nju.classroomassistant.student.network

import nju.classroomassistant.shared.PORT
import nju.classroomassistant.shared.log.Logger
import nju.classroomassistant.shared.messages.login.LoginMessage
import nju.classroomassistant.shared.messages.login.LoginResponse
import nju.classroomassistant.shared.messages.login.LoginResponseMessage
import nju.classroomassistant.shared.messages.Message
import java.io.Closeable
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket

class SocketClient(
    val studentId: String
): Closeable, Logger {

    companion object {
        var current: SocketClient? = null
    }

    val socket = Socket("10.0.2.2", PORT)
    val out: ObjectOutputStream
    val `in`: ObjectInputStream

    override fun close() {
        socket.close()
    }

    init {
        out = ObjectOutputStream(socket.getOutputStream())
        `in` = ObjectInputStream(socket.getInputStream())
    }

    fun login(): LoginResponse {
        writeToServer(LoginMessage(studentId))

        return when (val reply = readFromServer()) {
            is LoginResponseMessage -> {
                reply.response
            }
            else -> {
                error("收到错误的信息：$reply")
                LoginResponse.ERROR
            }
        }

    }

    fun writeToServer(message: Message) {
        out.writeObject(message)
    }

    fun readFromServer(): Message {
        return `in`.readObject() as Message
    }

}