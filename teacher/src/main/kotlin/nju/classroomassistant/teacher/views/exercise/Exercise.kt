package nju.classroomassistant.teacher.views.exercise

import tornadofx.*

class Exercise : View("练习") {

    override val root = pane {
        label("这是${title}页面")
    }
}
