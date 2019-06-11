package nju.classroomassistant.teacher.views.login

import com.jfoenix.controls.JFXButton
import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.event.ActionEvent
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.text.Font
import kfoenix.jfxbutton
import kfoenix.jfxcombobox
import kfoenix.jfxtextfield
import nju.classroomassistant.shared.log.Logger
import nju.classroomassistant.teacher.TeacherApp
import nju.classroomassistant.teacher.extensions.makeDraggable
import nju.classroomassistant.teacher.extensions.makeResizeable
import nju.classroomassistant.teacher.views.common.MainView
import tornadofx.*
import java.util.*

class LoginView : View("My View"), Observer, Logger {
    override fun update(o: Observable?, arg: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDock() {

        primaryStage.makeResizeable()
        primaryStage.makeDraggable(root)
    }

    private val studentId = SimpleStringProperty("")

    override val root = find<LoginCommonView>(mapOf(
        LoginCommonView::center to vbox {
            alignment = Pos.CENTER

            prefHeight = 80.0
            prefWidth = 40.0
            spacing = 40.0

            hbox {
                alignment = Pos.CENTER
                spacing = 10.0

                this += MaterialIconView(MaterialIcon.PERSON, "32")

                jfxtextfield(studentId, "学号", true) {
                    prefHeight = 32.0
                    prefWidth = 300.0
                }
            }
        },
        LoginCommonView::bottom to vbox {
            alignment = Pos.TOP_CENTER
            spacing = 20.0
            prefWidth = 340.0

            jfxbutton("登录") {
                setOnAction {
                    replaceWith<MainView>(sizeToScene = true, centerOnScreen = true)
                }
                prefHeight = 32.0
                prefWidth = 340.0
                style {
                    backgroundColor += c("#3F51B5")
                    textFill = c("#FFFFFF")
                }

                graphic = MaterialIconView(MaterialIcon.CHECK, "24").apply {
                    fill = c("#FFFFFF")
                }
            }

            jfxbutton("退出", JFXButton.ButtonType.RAISED) {
                setOnAction {
                    TeacherApp.exit()
                }
                prefHeight = 32.0
                prefWidth = 340.0

                style {
                    backgroundColor += c("E8EAF6")
                    textFill = c("#000000")
                }
            }
        }
    )).root
}
