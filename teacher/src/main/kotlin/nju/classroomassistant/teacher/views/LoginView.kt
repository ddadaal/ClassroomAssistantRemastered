package nju.classroomassistant.teacher.views

import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import kfoenix.jfxbutton
import nju.classroomassistant.teacher.network.Server
import nju.classroomassistant.teacher.network.StudentMap
import tornadofx.*
import java.util.*

class LoginView : View("My View"), Observer {
    override fun update(o: Observable?, arg: Any?) {
        if (o is StudentMap) {
            loggedCount.value = o.allStudents.size
        }
    }

    val loggedCount = SimpleIntegerProperty()

    override val root = vbox {
        jfxbutton(loggedCount.stringBinding { "当前登录人数$it "}) {
            action {
                // try importing shared classes and it's working!

            }
        }
    }

    override fun onDock() {
        Server.studentMap.addObserver(this)
    }

    override fun onUndock() {
        Server.studentMap.deleteObserver(this)
    }
}
