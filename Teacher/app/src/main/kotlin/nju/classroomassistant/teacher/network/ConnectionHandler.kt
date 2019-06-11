package nju.classroomassistant.teacher.network

import nju.classroomassistant.shared.log.Logger
import nju.classroomassistant.shared.messages.*
import nju.classroomassistant.shared.messages.discussion.DiscussionEndMessage
import nju.classroomassistant.shared.messages.discussion.DiscussionStartMessage
import nju.classroomassistant.shared.messages.discussion.StudentSendDiscussionMessage
import nju.classroomassistant.shared.messages.login.LoginMessage
import nju.classroomassistant.shared.messages.login.LoginResponseMessage
import nju.classroomassistant.shared.messages.login.LogoutMessage
import java.io.Closeable
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket


class ConnectionHandler(val socketClient: Socket, val studentMap: StudentMap) : Runnable, Logger, Closeable {

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
                        verbose("User ${message.studentId} logs in")

                        // 登录后给客户端如下的Message
                        // 1. 登录响应
                        writeMessage(LoginResponseMessage(LoginResponseMessage.Response.OK))

                        // 2. 如果目前讨论正在进行，发一个DiscussionStartMessage通知客户端；如果没有（客户端默认没有开始），就什么都不发
                        if (GlobalVariables.discussionStart) {
                            writeMessage(DiscussionStartMessage())
                        }

                        // 3. 如果目前做题正在进行，发一个ExerciseStartMessage通知客户端；如果没有进行（客户端默认没有进行），就什么都不发

                        // 4. 发一个NotificationSettingChangeMessage，告诉客户端目前实时提醒是否已经打开


                        // 记录登录信息
                        studentId = message.studentId
                        studentMap.login(message.studentId, this)
                    }
                    is LogoutMessage -> {
                        studentMap.logout(studentId!!)
                    }
                    // login message handler here

                    is StudentSendDiscussionMessage -> {
                        verbose("User sends the message ${message.content} to server")
                        if (GlobalVariables.discussionStart) {
                            // Add this message to the queue
                            GlobalVariables.currentDiscussionQueue.add(message.content)
                        }
                    }

                    else -> error("Non-supported message type: ${message.javaClass.simpleName}")
                }
            } catch (e: IOException) {
                // 客户端断了，算登出

                verbose("User $studentId disconnected unexpectedly")
                studentMap.logout(studentId!!)
                terminated = true
            }

        }
    }

    fun readMessage(): Message {
        val m = `in`.readObject() as Message
        verbose("Message Received: $m")
        return m
    }

    fun writeMessage(message: Message) {
        out.writeObject(message)
    }


}