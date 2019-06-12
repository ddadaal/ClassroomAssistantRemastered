package nju.classroomassistant.teacher.views.login

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXPasswordField
import com.jfoenix.controls.JFXTextField
import com.jfoenix.validation.RequiredFieldValidator
import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import javafx.scene.text.Font
import kfoenix.jfxbutton
import kfoenix.jfxpasswordfield
import kfoenix.jfxtextfield
import nju.classroomassistant.teacher.extensions.makeDraggable
import nju.classroomassistant.teacher.extensions.makeResizeable
import nju.classroomassistant.teacher.models.CourseInfo
import nju.classroomassistant.teacher.network.GlobalVariables
import nju.classroomassistant.teacher.repository.CourseInfoRepository
import nju.classroomassistant.teacher.repository.TeacherIdHistoryRepository
import nju.classroomassistant.teacher.util.executeLater
import tornadofx.*

class ImportFromJwView : View("从教务网导入") {

    val switchTo: (page: LoginRelatedPage, direction: ViewTransition.Direction) -> Unit by param()


    val idValidator = RequiredFieldValidator().apply {
        message = "请输入教师ID"
    }

    val passwordValidator = RequiredFieldValidator().apply {
        message = "请输入密码"
    }

    private val idProperty = SimpleStringProperty("")
    private val passwordProperty = SimpleStringProperty("")
    private val requestingProperty = SimpleBooleanProperty(false)

    var idField: JFXTextField by singleAssign()
    var passwordField: JFXPasswordField by singleAssign()


    override fun onDock() {
        primaryStage.makeResizeable()
        primaryStage.makeDraggable(root)

        idProperty.set(GlobalVariables.teacherId.get())
        passwordProperty.set("")
        passwordField.requestFocus()
    }


    fun login() {

        if (!idField.validate() || !passwordField.validate()) {
            return
        }

        requestingProperty.set(true)

        // mock
        CourseInfoRepository.data[GlobalVariables.teacherId.get()] = arrayListOf(
                CourseInfo("软件测试", "星期一，星期五"),
                CourseInfo("人机交互", "星期二"),
                CourseInfo("实证软件工程", "星期二，星期四"),
                CourseInfo("移动互联网", "星期三")
        )
        CourseInfoRepository.save()


        executeLater(500) {
            requestingProperty.set(false)

            // 登录，设置全局变量，跳到课程选择界面
            switchTo(LoginRelatedPage.COURSE_SELECTION, ViewTransition.Direction.RIGHT)
        }


    }

    override val root = vbox {

        alignment = Pos.BOTTOM_CENTER

        prefHeight = 1000.0
        spacing = 200.0


        vbox {

            spacing = 40.0

            alignment = Pos.TOP_CENTER

            label("从教务网导入") {
                font = Font(28.0)
            }

            hbox {
                alignment = Pos.CENTER
                spacing = 10.0

                this += MaterialIconView(MaterialIcon.PERSON, "32")

                idField = jfxtextfield(this@ImportFromJwView.idProperty, "教师编号", true) {
                    validators.add(idValidator)


                    prefHeight = 32.0
                    prefWidth = 300.0

                    focusedProperty().addListener { _, _, newValue ->
                        if (!newValue) {
                            this@jfxtextfield.validate()
                        }
                    }

                    enableWhen { requestingProperty.not() }

                    setOnKeyPressed {
                        if (it.code == KeyCode.ENTER) {
                            login()
                        }
                    }
                }
            }

            hbox {
                alignment = Pos.CENTER
                spacing = 10.0

                this += MaterialIconView(MaterialIcon.LOCK, "32")

                passwordField = jfxpasswordfield(this@ImportFromJwView.passwordProperty) {

                    isLabelFloat = true
                    promptText = "密码"

                    validators.add(passwordValidator)

                    prefHeight = 32.0
                    prefWidth = 300.0

                    focusedProperty().addListener { _, _, newValue ->
                        if (!newValue) {
                            this@jfxpasswordfield.validate()
                        }
                    }

                    enableWhen { requestingProperty.not() }

                    setOnKeyPressed {
                        if (it.code == KeyCode.ENTER) {
                            login()
                        }
                    }
                }
            }
        }



        vbox {
            spacing = 20.0

            paddingTop = 40.0

            jfxbutton(requestingProperty.stringBinding {
                if (it == true) {
                    "登录中"
                } else {
                    "登录"
                }
            }) {
                setOnAction {
                    login()
                }

                enableWhen { requestingProperty.not() }

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

            jfxbutton("返回课程选择", JFXButton.ButtonType.RAISED) {
                setOnAction {
                    switchTo(LoginRelatedPage.COURSE_SELECTION, ViewTransition.Direction.RIGHT)
                }
                prefHeight = 32.0
                prefWidth = 340.0

                style {
                    backgroundColor += c("E8EAF6")
                    textFill = c("#000000")
                }

                graphic = MaterialIconView(MaterialIcon.ARROW_BACK, "24")
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

                graphic = MaterialIconView(MaterialIcon.EXIT_TO_APP, "24")

            }
        }


    }

}