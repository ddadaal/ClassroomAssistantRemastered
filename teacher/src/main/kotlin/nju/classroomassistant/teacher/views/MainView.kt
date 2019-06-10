package nju.classroomassistant.teacher.views


import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import kfoenix.jfxbutton
import nju.classroomassistant.teacher.extension.Page
import nju.classroomassistant.teacher.extension.switchTo
import nju.classroomassistant.teacher.views.discussion.Discussion
import nju.classroomassistant.teacher.views.exercise.ExerciseInitView
import nju.classroomassistant.teacher.views.question.Question
import tornadofx.*


class MainView : View("Main View") {
    val loginView: LoginView by inject()



    override val root: BorderPane = borderpane {
        center = find(Page.EXERCISE.controller!!).view.root

        left = vbox {
            for (page in Page.values()) {
                jfxbutton(page.title).setOnAction {
                    center.switchTo(page)
                }
            }
        }

        prefWidth=800.0
        prefHeight=600.0
        style {
            font = Font.font(14.0)
        }
    }

}
