package nju.classroomassistant.teacher.views.question

import nju.classroomassistant.teacher.extensions.PageController
import tornadofx.*

class Question : View("提问") {
    override val root = pane {
        label("这是${title}页面")
    }
}

class QuestionController: PageController(Question::class)
