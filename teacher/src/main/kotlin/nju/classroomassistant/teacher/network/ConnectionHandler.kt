package nju.classroomassistant.teacher.network

import nju.classroomassistant.shared.messages.LoginMessage
import nju.classroomassistant.shared.messages.LogoutMessage
import nju.classroomassistant.shared.messages.Message
import nju.classroomassistant.teacher.log.Logger
import java.io.Closeable
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket


class ConnectionHandler(val socketClient: Socket, val studentMap: StudentMap): Runnable, Logger, Closeable {

    override fun close() {
        socketClient.close()
    }

    private val `in` = ObjectInputStream(socketClient.getInputStream())
    private val out = ObjectOutputStream(socketClient.getOutputStream())

    var studentId: String? = null

    var terminated = false


    override fun run() {
        while (!terminated) {
            try {
                when (val message = readMessage()) {
                    is LoginMessage -> {
                        studentId = message.studentId
                        studentMap.login(message.studentId, this)
                    }
                    is LogoutMessage -> {
                        studentMap.logout(studentId!!)
                    }
                    // login message handler here

                    else -> error("Non-supported message type: ${message.javaClass.simpleName}")
                }
            } catch (e: IOException) {
                // 客户端断了，算登出

                verbose("User $studentId disconnected unexpectedly")
                studentMap.logout(studentId!!)
            }

        }
    }

    private fun readMessage(): Message {
        val m = `in`.readObject() as Message
        verbose("Message Received: $m")
        return m
    }


}