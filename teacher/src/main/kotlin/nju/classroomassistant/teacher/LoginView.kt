package nju.classroomassistant.teacher

import kfoenix.jfxbutton
import nju.classroomassistant.shared.call.TestCall
import tornadofx.*

class LoginView : View("My View") {
    override val root = vbox {
        jfxbutton("Material按钮") {
            action {
                // try importing shared classes and it's working!
                val call = TestCall("123")
                println(call.value)
            }
        }
    }
}
