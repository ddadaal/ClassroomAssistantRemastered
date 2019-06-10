package nju.classroomassistant.teacher.views

import javafx.scene.layout.BorderPane
import kfoenix.jfxbutton
import nju.classroomassistant.teacher.views.discussion.Discussion
import nju.classroomassistant.teacher.views.exercise.Exercise
import nju.classroomassistant.teacher.views.login.LoginView
import nju.classroomassistant.teacher.views.question.Question
import tornadofx.*


class MainView : View("Main View") {
    val loginView: LoginView by inject()
    val controller: MainController by inject()


    override val root: BorderPane = borderpane {
        right = controller.findView().root

        bottom = loginView.root

        left = vbox {
            for (i in controller.views.indices) {
                jfxbutton(controller.findView(i).title).setOnAction {
                    controller.index =i
                    right.replaceWith(controller.findView().root)
                }
            }
        }

        prefWidth=800.0
        prefHeight=600.0
    }

}


class MainController : Controller() {
    val views = arrayOf(
        Exercise::class,
        Discussion::class,
        Question::class
    )

    var index = 0

    fun findView(i: Int = index) = find(views[i])
}