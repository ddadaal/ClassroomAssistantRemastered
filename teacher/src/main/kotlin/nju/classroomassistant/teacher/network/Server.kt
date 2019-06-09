package nju.classroomassistant.teacher.network

import nju.classroomassistant.shared.PORT
import nju.classroomassistant.shared.log.Logger
import java.net.ServerSocket

object Server: Logger {

    // 在这里注册系统的状态

    // 学生列表
    val studentMap = StudentMap()

    fun start() {
        val socketServer = ServerSocket(PORT)
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
    }
}