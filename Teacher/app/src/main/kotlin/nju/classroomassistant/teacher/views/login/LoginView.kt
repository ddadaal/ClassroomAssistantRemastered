package nju.classroomassistant.teacher.views.login

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import com.jfoenix.validation.RequiredFieldValidator
import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import kfoenix.jfxbutton
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

    val idValidator = RequiredFieldValidator().apply {
        message = "请输入教师ID"
    }

    override fun onDock() {
        primaryStage.makeResizeable()
        primaryStage.makeDraggable(root)
    }

    private val idProperty = SimpleStringProperty("")
    private val loggingInProperty = SimpleBooleanProperty(false)

    var idField: JFXTextField by singleAssign()

    fun login() {

        if (!idField.validate()) {
            return
        }

        loggingInProperty.set(true)

        // login




        replaceWith<MainView>(sizeToScene = true, centerOnScreen = true)
    }

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

                idField = jfxtextfield(this@LoginView.idProperty, "学号", true) {
                    validators.add(idValidator)

                    prefHeight = 32.0
                    prefWidth = 300.0

                    focusedProperty().addListener { _, _, newValue ->
                        if (!newValue) {
                            this@jfxtextfield.validate()
                        }
                    }

                    enableWhen { loggingInProperty.booleanBinding { it != true } }

                    setOnKeyPressed {
                        if (it.code == KeyCode.ENTER) {
                            login()
                        }
                    }
                }
            }
        },
        LoginCommonView::bottom to vbox {
            alignment = Pos.TOP_CENTER
            spacing = 20.0
            prefWidth = 340.0

            jfxbutton(loggingInProperty.stringBinding { if (it == true) { "登录中" } else { "登录"} }) {
                setOnAction {
                    login()
                }

                enableWhen { loggingInProperty.booleanBinding { it != true }}

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
                    primaryStage.close()
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
