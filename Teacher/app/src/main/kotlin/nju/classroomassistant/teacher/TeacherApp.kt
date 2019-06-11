package nju.classroomassistant.teacher

import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.stage.Stage
import nju.classroomassistant.teacher.network.Server
import nju.classroomassistant.teacher.views.MainView
import nju.classroomassistant.teacher.views.login.LoginView
import tornadofx.*

class MyApp: App(LoginView::class,MyStyle::class){
    init {
        reloadStylesheetsOnFocus()
    }

    override fun start(stage: Stage) {
        super.start(stage)
    }
}

fun main(args: Array<String>) {
    // Start Server
    Thread { Server.start() }.start()


    launch<MyApp>(args)

}
class MyStyle: Stylesheet() {

    companion object {
        val `blue-spinner` by cssclass()
        private val arc by cssclass()
        private val track by cssclass()
    }

    init {
        `blue-spinner`{
            arc{
                stroke= Color.RED
            }

            track{
                                stroke=Color.BLACK
            }
        }

    }
}