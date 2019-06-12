package nju.classroomassistant.teacher.network

import nju.classroomassistant.shared.Config.PORT
import nju.classroomassistant.shared.log.Logger
import java.net.ServerSocket
import java.net.SocketException

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


    fun start() {
        thread.start()
    }

    fun stop() {
        socketServer.close()
    }
}