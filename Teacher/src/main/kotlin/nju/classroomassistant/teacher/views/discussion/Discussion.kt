package nju.classroomassistant.teacher.views.discussion

import com.jfoenix.controls.JFXButton
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.SelectionMode
import javafx.scene.paint.Color
import kfoenix.jfxbutton
import kfoenix.jfxlistview
import kfoenix.jfxtextfield
import nju.classroomassistant.teacher.network.GlobalVariables
import nju.classroomassistant.teacher.network.Server
import tornadofx.*

class Discussion : View("讨论") {

    private val controller: DiscussionController by inject()

    private val chatItems = GlobalVariables.currentDiscussionQueue

    override val root = vbox {
        useMaxWidth = true
        useMaxHeight = true

        label("这是${title}页面")

        val textField = jfxtextfield(promptText = "讨论标题") {
            isLabelFloat = true
            isDisable = !GlobalVariables.discussionStart

            style {
                fontSize = 20.px
                alignment = Pos.BOTTOM_CENTER
                paddingTop = 20
            }
        }

        scrollpane {
            jfxlistview(chatItems) {
                selectionModel.selectionMode = SelectionMode.SINGLE
            }

            style {
                startMargin = 20.px
            }
        }

        vbox(spacing = 20) {
            padding = Insets(30.0, 5.0, 30.0, 5.0)

            jfxbutton(value = "开始", btnType = JFXButton.ButtonType.RAISED) {
                setPrefSize(100.0, 40.0)
                // set on action here
                action {
                    val isStart = GlobalVariables.discussionStart
                    text = if (isStart) "开始" else "结束"
                    textField.isDisable = !isStart
                    GlobalVariables.discussionStart = !isStart
                }

                style {
                    textFill = Color.WHITE
                    backgroundColor += c("#5264AE")
                    fontSize = 14.px
                    paddingRight = 10
                }
            }

            alignment = Pos.BOTTOM_RIGHT
        }
    }

    private fun sendStartMessages() {
        Server.studentMap.allStudents.forEach {
            it.handler
        }
    }
}

class DiscussionController : Controller() {

    fun getDiscussionContent() = listOf("A: aaaaa", "B: bbbbb", "C: cccccc")
}