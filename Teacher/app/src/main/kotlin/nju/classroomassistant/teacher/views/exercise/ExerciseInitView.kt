package nju.classroomassistant.teacher.views.exercise

import com.jfoenix.controls.JFXButton
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment
import javafx.stage.StageStyle
import kfoenix.jfxbutton
import kfoenix.jfxcombobox
import kfoenix.jfxradiobutton
import nju.classroomassistant.shared.messages.exercise.ExerciseStartMessage
import nju.classroomassistant.shared.messages.exercise.type.ChoiceExerciseType
import nju.classroomassistant.shared.messages.exercise.type.FillBlankExerciseType
import nju.classroomassistant.teacher.network.GlobalVariables
import nju.classroomassistant.teacher.network.Server
import tornadofx.*


class ExerciseInitView : View("练习") {
    val isBlankProperty = SimpleBooleanProperty(true)
    val choiceNumberProperty = SimpleIntegerProperty(4)

    val controller: ExerciseController by inject()

    override val root = vbox {

        label("选择题型") {
            font = Font(28.0)
        }

        borderpane {
            left=label("题型") {
                textAlignment = TextAlignment.LEFT
                prefWidth = 80.0
            }
            right= hbox{
                togglegroup {
                    jfxradiobutton("填空题"){
                        setOnAction {
                            isBlankProperty.set(true)
                        }
                        isSelected=true
                    }


                    jfxradiobutton("选择题")
                            .setOnAction {
                                isBlankProperty.set(false)
                            }
                    spacing = 20.0
                }
            }
            alignment = Pos.CENTER
        }

        borderpane {
            removeWhen(isBlankProperty)
            left = label("选项个数") {
                paddingTop = 10.0
            }
            right = jfxcombobox(choiceNumberProperty, values = arrayListOf(2, 3, 4, 5, 6)) {
                prefWidth = 150.0
            }


        }

        jfxbutton("发布", JFXButton.ButtonType.RAISED) {

            action {
                publish()
            }
            prefWidth = 300.0
            prefHeight = 30.0

            style {
                backgroundColor += c("#5264AE")
                textFill = Color.WHITE
            }
        }


        maxWidth = 400.0
//        maxHeight = 400.0
        spacing = 40.0


    }

    private fun publish() {

        val exercise = if (isBlankProperty.get()) {
            FillBlankExerciseType()
        } else {
            ChoiceExerciseType(choiceNumberProperty.get())
        }

        GlobalVariables.exerciseSession.start(exercise)

   //     Server.writeToAllStudentsAsync(ExerciseStartMessage(exercise))

        controller.to<CollectingView>()

    }

}
