package nju.classroomassistant.student.network

import nju.classroomassistant.shared.PORT
import nju.classroomassistant.shared.log.Logger
import nju.classroomassistant.shared.messages.login.LoginMessage
import nju.classroomassistant.shared.messages.login.LoginResponse
import nju.classroomassistant.shared.messages.login.LoginResponseMessage
import nju.classroomassistant.shared.messages.Message
import nju.classroomassistant.shared.messages.discussion.DiscussionEndMessage
import nju.classroomassistant.shared.messages.discussion.DiscussionStartMessage
import nju.classroomassistant.shared.messages.exercise.ExerciseEndMessage
import nju.classroomassistant.shared.messages.exercise.ExerciseStartMessage
import nju.classroomassistant.shared.messages.raisequestion.NotificationSettingChangeMessage
import nju.classroomassistant.student.systemstate.SystemState
import java.io.Closeable
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket

class SocketClient: Closeable, Logger {

    val socket = Socket("10.0.2.2", PORT)
    val out: ObjectOutputStream
    val `in`: ObjectInputStream

    var terminated = false

    val listeningThread = Thread {
        while (!terminated) {
            when (val reply = readFromServer()) {
                is DiscussionStartMessage -> {
                    SystemState.discussion.started = true
                }
                is DiscussionEndMessage -> {
                    SystemState.discussion.started = false
                }
                is ExerciseStartMessage -> {
                    SystemState.exercise.exerciseType = reply.exerciseType
                }
                is ExerciseEndMessage -> {
                    SystemState.exercise.exerciseType = null
                }
                is NotificationSettingChangeMessage -> {
                    SystemState.raiseQuestion.instantNotificationEnabled = reply.instantNotificationEnabled
                }
            }
        }
    }

    override fun close() {
        terminate()
        socket.close()
    }

    init {
        out = ObjectOutputStream(socket.getOutputStream())
        `in` = ObjectInputStream(socket.getInputStream())
    }

    fun startListening() {
        terminated = false
        listeningThread.start()
    }

    fun terminate() {
        terminated = true
    }

    fun login(): LoginResponse {
        writeToServer(LoginMessage(SystemState.studentId))

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