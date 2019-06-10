package nju.classroomassistant.teacher

import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import nju.classroomassistant.teacher.network.Server
import nju.classroomassistant.teacher.views.LoginView
import nju.classroomassistant.teacher.views.MainView
import tornadofx.*

class MyApp: App(MainView::class,MyStyle::class){
    init {
        reloadStylesheetsOnFocus()
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