package nju.classroomassistant.teacher

import tornadofx.App
import tornadofx.launch

class MyApp: App(LoginView::class)

fun main(args: Array<String>) {
    launch<MyApp>(args)
}