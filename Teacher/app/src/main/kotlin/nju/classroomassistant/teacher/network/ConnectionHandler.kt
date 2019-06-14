package nju.classroomassistant.teacher.network

import nju.classroomassistant.shared.log.Logger
import nju.classroomassistant.shared.messages.Message
import nju.classroomassistant.shared.messages.discussion.DiscussionStartMessage
import nju.classroomassistant.shared.messages.discussion.StudentSendDiscussionMessage
import nju.classroomassistant.shared.messages.exercise.ExerciseStartMessage
import nju.classroomassistant.shared.messages.exercise.ExerciseSubmitMessage
import nju.classroomassistant.shared.messages.login.LoginMessage
import nju.classroomassistant.shared.messages.login.LoginResponseMessage
import nju.classroomassistant.shared.messages.login.LogoutMessage
import nju.classroomassistant.shared.messages.raisequestion.NotificationSettingChangeMessage
import nju.classroomassistant.shared.messages.raisequestion.StudentRaiseQuestionMessage
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

    var student: StudentItem? = null

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
                        if (GlobalVariables.discussionSession.started.get()) {
                            writeMessage(DiscussionStartMessage())
                        }

                        // 3. 如果目前做题正在进行，发一个ExerciseStartMessage通知客户端；如果没有进行（客户端默认没有进行），就什么都不发
                        if (GlobalVariables.exerciseSession.started) {
                            writeMessage(ExerciseStartMessage(GlobalVariables.exerciseSession.exercise))
                        }

                        // 4. 发一个NotificationSettingChangeMessage，告诉客户端目前实时提醒是否已经打开
                        writeMessage(NotificationSettingChangeMessage(GlobalVariables.questionSession.isNotificationOpen.get()))

                        // 记录登录信息
                        student = studentMap.login(message.studentId, this)
                    }
                    is LogoutMessage -> {
                        student?.let {
                            studentMap.logout(it.studentId)
                        }
                    }
                    // login message handler here

                    is StudentSendDiscussionMessage -> {
                        verbose("$student sends the message ${message.content} to server")
                        student?.let {
                            if (GlobalVariables.discussionSession.started.get()) {
                                // Add this message to the queue
                                GlobalVariables.discussionSession.add(it, message)
                            }
                        }

                    }
                    is ExerciseSubmitMessage -> {
                        verbose("$student submits exercise answer")

                        student?.let {
                            GlobalVariables.exerciseSession.add(it, message.answer)
                        }

                    }

                    is StudentRaiseQuestionMessage -> {
                        verbose("$student raised a question: ${message.question}")
                        student?.let {
                            GlobalVariables.questionSession.addQuestion(message.question, it.studentId)
                        }
                    }

                    else -> error("Non-supported message type: ${message.javaClass.simpleName}")
                }
            } catch (e: IOException) {
                // 客户端断了，算登出

                verbose("User $student disconnected unexpectedly")
                student?.let {
                    studentMap.logout(it.studentId)
                }
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