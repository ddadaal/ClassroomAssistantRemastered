package nju.classroomassistant.teacher.network

import javafx.concurrent.Task
import nju.classroomassistant.shared.Config.PORT
import nju.classroomassistant.shared.log.Logger
import nju.classroomassistant.shared.messages.Message
import tornadofx.*
import java.io.IOException
import java.net.ServerSocket
import java.net.SocketException


const val MOCK = true

object Server: Logger {

    // 在这里注册系统的状态

    // 学生列表
    val studentMap = StudentMap()

    val socketServer = ServerSocket(PORT)

    val thread = Thread {
        try {
            verbose("Started server on $PORT")


            while (true) {
                val socketClient = socketServer.accept()
                verbose("Accept connection from ${socketClient.remoteSocketAddress}")

                Thread(
                        ConnectionHandler(
                                socketClient,
                                studentMap
                        )
                ).start()
            }
        } catch (e: SocketException) {
            verbose("Exiting server...")
        }

    }

    fun writeToAllStudentsAsync(message: Message): Task<Unit> {
        return FXTask {
            if (!MOCK) {
                studentMap.allStudents.forEach {
                    it.handler.writeMessage(message)
                }
            }

        }


    }


    fun start() {
        thread.start()
    }

    fun stop() {
        socketServer.close()
    }
}