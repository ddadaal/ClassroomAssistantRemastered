package nju.classroomassistant.teacher.views.question

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXListView
import com.jfoenix.svg.SVGGlyph
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import javafx.stage.StageStyle
import kfoenix.jfxbutton
import kfoenix.jfxlistview
import kfoenix.jfxtextfield
import kfoenix.jfxtogglebutton
import nju.classroomassistant.teacher.extensions.PageController
import nju.classroomassistant.teacher.extensions.asLargeAsPossible
import nju.classroomassistant.teacher.network.GlobalVariables
import tornadofx.*
import javafx.scene.Scene
import com.sun.javafx.robot.impl.FXRobotHelper.getChildren
import javafx.scene.control.Button
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import nju.classroomassistant.teacher.network.session.QuestionItem
import java.time.format.DateTimeFormatter


class QuestionView : View("提问") {

    private val session = GlobalVariables.questionSession

    private val minusCircleSVGPath = "M694.857 402.286v73.143q0 14.857-10.857 25.714t-25.714 10.857h-438.857q-14.857" +
            " 0-25.714-10.857t-10.857-25.714v-73.143q0-14.857 10.857-25.714t25.714-10." +
            "857h438.857q14.857 0 25.714 10.857t10.857 25.714zM877.714 438.857q0-119.429" +
            "-58.857-220.286t-159.714-159.714-220.286-58.857-220.286 58.857-159.714 " +
            "159.714-58.857 220.286 58.857 220.286 159.714 159.714 220.286 58.857 " +
            "220.286-58.857 159.714-159.714 58.857-220.286z"

    override val root = borderpane {

        asLargeAsPossible()

        center = jfxlistview(session.questionList) {
            println("question list is ${session.questionList}")
            paddingAll = 20.0

            cellFormat {
                graphic = cache(it) {

                    stackpane {

                        setOnMouseClicked {
                            opacity = 0.5
                        }

                        onDoubleClick {
                            find<DialogFragment>(DialogFragment::questionItem to it).openWindow(StageStyle.TRANSPARENT)
                        }

                        // Show student id and question's content
                        vbox {

                            paddingAll = 8.0

                            spacing = 8.0

                            label("${it.studentNickname} 于 ${it.time.format(DateTimeFormatter.ofPattern("HH:mm:ss"))} 提出问题：") {

                            }
                            label(it.content) {
                                style {
                                    fontSize = 18.px
                                    fontWeight = FontWeight.BOLD
                                }
                            }
                        }


                        // Delete button
                        jfxbutton(btnType = JFXButton.ButtonType.RAISED) {
                            ripplerFill = c("C74C48")
                            scaleX = 1.0
                            scaleY = 1.0

                            StackPane.setMargin(this, Insets(0.0, 10.0, 0.0, 0.0))
                            StackPane.setAlignment(this, Pos.TOP_RIGHT)


                            // Set svg image
                            val glyph = SVGGlyph(-1, "minus-circle", minusCircleSVGPath, c("C74C48"))
                            setPrefSize(15.0, 15.0)
                            glyph.setSize(20.0, 20.0)
                            graphic = glyph

                            action {
                                session.questionList.removeAt(index)
                            }

                            style {
                                backgroundRadius += box(40.px)
                            }

                        }

                    }
                }
            }
        }


        bottom = hbox {
            spacing = 10.0
            alignment = Pos.CENTER

            jfxtogglebutton {
                text = "启用提问实时提醒"
                alignment = Pos.CENTER
                prefHeight = 30.0
                selectedProperty().bindBidirectional(session.isNotificationOpen)
            }

            jfxbutton("清空", JFXButton.ButtonType.RAISED) {
                setPrefSize(300.0, 30.0)

                action {
                    // Clear all questions
                    session.questionList.clear()
                }

                style {
                    backgroundColor += c("C74C48")
                    textFill = Color.WHITE
                    alignment = Pos.CENTER
                }
            }
        }
    }
}

class QuestionController : PageController(QuestionView::class)

