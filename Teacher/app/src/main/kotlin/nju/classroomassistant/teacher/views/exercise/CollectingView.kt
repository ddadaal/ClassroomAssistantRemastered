package nju.classroomassistant.teacher.views.exercise

import com.jfoenix.controls.JFXButton
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import kfoenix.jfxbutton
import kfoenix.jfxspinner
import nju.classroomassistant.shared.messages.exercise.ExerciseEndMessage
import nju.classroomassistant.teacher.network.GlobalVariables
import nju.classroomassistant.teacher.network.Server
import nju.classroomassistant.teacher.views.common.MainCss
import tornadofx.*
import tornadofx.Stylesheet.Companion.button
import java.util.concurrent.Callable
import javax.swing.text.html.StyleSheet


class CollectingView : View("Collecting View") {

    val controller: ExerciseController by inject()

    val session = GlobalVariables.exerciseSession


    override val root = vbox {

        alignment = Pos.CENTER

        maxWidth = 500.0
        maxHeight = 500.0
        spacing = 40.0

        borderpane {
            left = label("作答人数")
            right = label(Bindings.createStringBinding(Callable { "${session.answers.size} / ${Server.studentMap.size}"  },
                    session.answers, Server.studentMap.studentMapObservable))
            style {
                fontSize = 16.px
            }
        }

        jfxspinner {
            progressProperty().bind(Bindings.createDoubleBinding(Callable { session.answers.size.toDouble() / Server.studentMap.size },
                    session.answers, Server.studentMap.studentMapObservable))
            setPrefSize(200.0, 200.0)
            addClass("blue-spinner")
        }

        jfxbutton("结束", JFXButton.ButtonType.RAISED) {
            setOnAction {
                finish()
            }
            prefWidth = 300.0
            prefHeight = 30.0

            style {
                backgroundColor += c("#D63333")
                textFill = Color.WHITE
            }
        }


    }

    private fun finish() {
        // 结束本session，通知所有客户端
        GlobalVariables.exerciseSession?.finish()
        Server.writeToAllStudentsAsync(ExerciseEndMessage())
        controller.to<ResultView>()
    }
}
