package nju.classroomassistant.teacher

import javafx.stage.Stage
import javafx.stage.StageStyle
import nju.classroomassistant.teacher.network.Server
import nju.classroomassistant.teacher.views.common.MainCss
import nju.classroomassistant.teacher.views.login.LoginView
import tornadofx.*

class TeacherApp: App(LoginView::class, MainCss::class){
    init {
        reloadStylesheetsOnFocus()
    }

    override fun start(stage: Stage) {
        TeacherApp.stage = stage
        stage.initStyle(StageStyle.UNDECORATED)

        super.start(stage)
    }

    companion object {

        val serverThread = Thread { Server.start() }
        lateinit var stage: Stage

        @JvmStatic
        fun main(args: Array<String>) {
            // Start Server
            serverThread.start()
            launch<TeacherApp>(args)
        }

        fun exit() {
            serverThread.interrupt()
            stage.close()
        }



    }
}

