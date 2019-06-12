package nju.classroomassistant.teacher.views.question

import com.jfoenix.controls.JFXButton
import javafx.geometry.Pos
import javafx.scene.paint.Color
import kfoenix.jfxbutton
import kfoenix.jfxlistview
import kfoenix.jfxtextfield
import kfoenix.jfxtogglebutton
import nju.classroomassistant.teacher.extensions.PageController
import nju.classroomassistant.teacher.network.QuestionSession
import tornadofx.*

class QuestionView : View("提问") {

    private val controller: QuestionController by inject()

    private val questionList = QuestionSession.questionList

    override val root = borderpane {

        importStylesheet("/css/main.css")

        center = jfxlistview(questionList) {

            cellFormat {
                graphic = cache {
                    form {
                        fieldset {
                            field("学生") {
                                label(it.studentId ?: "Unknown")
                            }

                            field("问题") {
                                jfxtextfield {
                                    isEditable = false
                                    text = it.content

                                    style {
                                        fontSize = 16.px

                                    }
                                }
                            }
                        }
                    }
                }

                graphic.setOnMouseClicked {
                    setOnMouseClicked {
                        if (it.clickCount == 2) {
                            // Use lower opacity to show that this cell has been visited
                            selectionModel.selectedItem.isVisited = true
                            opacity = 0.5

                            // TODO: Open a new window to show the details of this problem
                        }
                    }
                }
            }


        }

        bottom = hbox {
            jfxtogglebutton {
                text = "启用提问提醒"
                alignment = Pos.CENTER
                prefHeight = 30.0

                action {
                    QuestionSession.isNotificationOpen = !QuestionSession.isNotificationOpen
                    if (isSelected) {
                        // Open message notification
                    } else {
                        // Close message notification
                    }
                }
            }

            jfxbutton("清空", JFXButton.ButtonType.RAISED) {
                prefHeight = 30.0
                prefWidth = 300.0

                action {
                    // Clear all questions
                    questionList.clear()
                    center.getChildList()!!.clear()
                }

                style {
                    backgroundColor += c("C74C48")
                    textFill = Color.WHITE
                    alignment = Pos.CENTER
                }
            }

            alignment = Pos.CENTER

            spacing = 10.0
        }
    }
}

class QuestionController : PageController(QuestionView::class)

