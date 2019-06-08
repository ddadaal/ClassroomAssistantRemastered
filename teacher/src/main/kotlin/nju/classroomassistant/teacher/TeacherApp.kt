package nju.classroomassistant.teacher

import nju.classroomassistant.teacher.network.Server
import nju.classroomassistant.teacher.views.LoginView
import tornadofx.App
import tornadofx.launch

class MyApp: App(LoginView::class)

fun main(args: Array<String>) {
    // Start Server
    Thread { Server.start() }.start()

    launch<MyApp>(args)

}