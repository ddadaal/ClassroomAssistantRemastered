package nju.classroomassistant.teacher.views.question

import tornadofx.*

class Question : View("提问") {
    override val root = pane {
        label("这是${title}页面")
    }
}
