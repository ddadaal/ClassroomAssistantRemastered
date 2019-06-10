package nju.classroomassistant.teacher.views.discussion

import kfoenix.jfxbutton
import tornadofx.*

class Discussion : View("讨论") {
    override val root = pane {
        label("这是${title}页面")
    }
}
