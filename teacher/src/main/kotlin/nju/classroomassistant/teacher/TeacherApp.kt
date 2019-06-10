package nju.classroomassistant.teacher

import javafx.stage.Stage
import javafx.stage.StageStyle
import nju.classroomassistant.teacher.network.Server
import nju.classroomassistant.teacher.views.login.LoginView
import tornadofx.App
import tornadofx.launch

class MyApp: App(LoginView::class) {



    override fun start(stage: Stage) {


        println(MyApp::class.java.getResource("/fxml/LoginUi.fxml"))

        stage.initStyle(StageStyle.UNDECORATED)

        super.start(stage)
    }
}

fun main(args: Array<String>) {
    // Start Server
    Thread { Server.start() }.start()

    launch<MyApp>(args)

}