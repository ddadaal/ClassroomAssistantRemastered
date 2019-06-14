package nju.classroomassistant.teacher.views.discussion

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import javafx.collections.ListChangeListener
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.control.SelectionMode
import javafx.scene.control.TextArea
import javafx.scene.effect.BlurType
import javafx.scene.effect.DropShadow
import javafx.scene.layout.Border
import javafx.scene.layout.BorderStroke
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import kfoenix.jfxbutton
import kfoenix.jfxlistview
import kfoenix.jfxtextfield
import nju.classroomassistant.shared.messages.discussion.StudentSendDiscussionMessage
import nju.classroomassistant.teacher.extensions.PageController
import nju.classroomassistant.teacher.extensions.asLargeAsPossible
import nju.classroomassistant.teacher.network.GlobalVariables
import tornadofx.*

class DiscussionView : View("讨论") {

    val session = GlobalVariables.discussionSession


    var titleField: JFXTextField by singleAssign()

    override fun onDock() {
        super.onDock()
    }

    override val root = vbox {

        alignment = Pos.CENTER

        spacing = 16.0

        paddingAll = 16.0

        asLargeAsPossible()

        importStylesheet("/css/main.css")

        titleField = jfxtextfield(session.title) {
            text = "点击开始按钮开始一个讨论"
            isLabelFloat = true

            disableProperty().bind(session.started.not())

            style {
                fontSize = 20.px
                alignment = Pos.BOTTOM_CENTER
                paddingTop = 20
            }

        }

        stackpane {

            asLargeAsPossible()

//            paddingTop = 50

            jfxlistview(session.discussionItems) {

                placeholder = label(session.started.stringBinding {
                    if (it == true) {
                        "暂时没有讨论内容。"
                    } else {
                        "请点击开始按钮以开始一次讨论。"
                    }
                })

                selectionModel.selectionMode = SelectionMode.SINGLE

                session.discussionItems.addListener(ListChangeListener {
                    this.scrollTo(it.list.size - 1)
                })

//                style {
//                    effect = DropShadow(BlurType.THREE_PASS_BOX, Color.rgb(0, 0, 0, 0.8), 5.0, 0.0, 0.0, 0.0)
////                    backgroundRadius += box(5.px)
//                }

            }
        }


        vbox(spacing = 20) {
            padding = Insets(30.0, 5.0, 30.0, 5.0)
            alignment = Pos.CENTER
            maxWidth = 1000.0
            maxHeight = 1000.0

            jfxbutton(
                    session.started.stringBinding {
                        if (it == true) {
                            "结束"
                        } else {
                            "开始"
                        }
                    },
                    btnType = JFXButton.ButtonType.RAISED
            ) {
                setPrefSize(300.0, 30.0)
                textFill = Color.WHITE
                // set on action here


                session.started.addListener { _, _, newValue ->
                    style {
                        backgroundColor += if (newValue) {
                            c("C74C48")
                        } else {
                            c("5263AE")
                        }
                    }
                }


                style {
                    backgroundColor += c("5263AE")
                }

                action {
                    session.toggle()

                }
            }

        }

    }
}


class DiscussionItem(message: StudentSendDiscussionMessage, private val nickname: String?) {
    private val content = message.content

    override fun toString(): String {
        return "[$nickname] $content"
    }
}

class ChatCellFactory(root: Scene) : ListCell<Pair<String?, String>>() {

    override fun updateItem(value: Pair<String?, String>?, empty: Boolean) {
        if (graphic == null && value != null) {
            val textArea = TextArea()
            val grid = GridPane()
            val root = HBox()
            val nameBar = HBox()

            nameBar.alignment = Pos.TOP_LEFT
            nameBar.maxWidth = 20.0
            nameBar.add(Label(value.first))
            grid.add(nameBar, 0, 0)
            grid.add(textArea, 0, 1, 5, 1)
            root.add(nameBar)
            root.add(textArea)

//            textArea.maxWidthProperty().bind(widthProperty().minus(100))
            textArea.minHeight = 1.0
            textArea.prefHeight = 30.0
            textArea.isWrapText = true
            textArea.isEditable = false
            textArea.text = value.second
            setMine(grid)
            graphic = grid
        }
    }

    private fun setMine(hBox: GridPane) {
        hBox.alignment = Pos.BASELINE_RIGHT
        for (child in hBox.children) {
            if (child is TextArea) {
                child.style = "-fx-control-inner-background: #F5F6F8;"
                break
            }
        }
    }
}

class DiscussionController : PageController(DiscussionView::class)