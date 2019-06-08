package nju.classroomassistant.teacher.network

import nju.classroomassistant.shared.PORT
import nju.classroomassistant.teacher.log.Logger
import java.net.ServerSocket

object Server: Logger {

    private val socketServer: ServerSocket

    init {
        socketServer = ServerSocket(PORT)
        verbose("Started server on $PORT")
    }

    val studentMap = StudentMap()



    fun start() {
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