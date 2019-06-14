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
import javafx.scene.layout.StackPane
import kfoenix.jfxbadge
import kfoenix.jfxbutton
import kfoenix.jfxtextfield
import nju.classroomassistant.shared.log.Logger
import nju.classroomassistant.teacher.models.TeacherInfo
import tornadofx.*
import nju.classroomassistant.teacher.network.GlobalVariables
import nju.classroomassistant.teacher.repository.Repository
import nju.classroomassistant.teacher.repository.TeacherIdHistoryRepository
import nju.classroomassistant.teacher.repository.TeacherInfoRepository
import nju.classroomassistant.teacher.views.common.MainCss


class LoginView : View("登录"), Logger {


    val idValidator = RequiredFieldValidator().apply {
        message = "请输入教师ID"
    }

    val switchTo: (page: LoginRelatedPage, direction: ViewTransition.Direction) -> Unit by param()


    private val idProperty = SimpleStringProperty("")
    private val loggingInProperty = SimpleBooleanProperty(false)

    var idField: JFXTextField by singleAssign()


    override fun onDock() {
//        primaryStage.makeResizeable()
//        primaryStage.makeDraggable(root)

//        // initialize auto suggestion
//        val autoCompletePopup = JFXAutoCompletePopup<String>()
//        autoCompletePopup.suggestions.addAll(TeacherIdHistoryRepository.data.history.reversed())
//
//        autoCompletePopup.setSelectionHandler { event ->
//            idProperty.set(event.`object`)
//
//            // you can do other actions here when text completed
//        }
//
//
//        idField.setOnMouseClicked {
//            autoCompletePopup.filter { true }
//            autoCompletePopup.show(idField)
//        }
//
        idField.setOnKeyPressed {

            if (it.code == KeyCode.ENTER) {
                login()
            }
        }
//
//            autoCompletePopup.filter { string -> string.toLowerCase().contains(idField.text) }
//            if (autoCompletePopup.filteredSuggestions.isEmpty()) {
//                autoCompletePopup.hide()
//            } else {
//                autoCompletePopup.show(idField)
//            }
//
//        }

    }


    fun login() {

        if (!idField.validate()) {
            return
        }

        loggingInProperty.set(true)

        // 记录输入历史
        TeacherIdHistoryRepository.data.add(idProperty.get())
        TeacherIdHistoryRepository.save()


        loggingInProperty.set(false)

        // 登录，设置全局变量，跳到课程选择界面

        GlobalVariables.teacherInfo.set(TeacherInfoRepository.data.computeIfAbsent(idProperty.get()) {
            TeacherInfo(it, it, false, arrayListOf())
        })

        verbose("Selected teacher ${GlobalVariables.teacherInfo.get()}")

        TeacherInfoRepository.save()
        // 初始化Session

        switchTo(LoginRelatedPage.COURSE_SELECTION, ViewTransition.Direction.LEFT)
    }

    override val root = vbox {
        alignment = Pos.BOTTOM_CENTER

        prefHeight = 1000.0

        spacing = 200.0

        hbox {
            alignment = Pos.CENTER
            spacing = 10.0

            this += MaterialIconView(MaterialIcon.PERSON, "32")

            idField = jfxtextfield(this@LoginView.idProperty, "教师号", true) {
                validators.add(idValidator)

                prefHeight = 32.0
                prefWidth = 300.0

                focusedProperty().addListener { _, _, newValue ->
                    if (!newValue) {
                        this@jfxtextfield.validate()
                    }
                }

                enableWhen { loggingInProperty.not() }

            }
        }

        vbox {
            spacing = 20.0

            paddingTop = 80.0

            jfxbutton(loggingInProperty.stringBinding {
                if (it == true) {
                    "登录中"
                } else {
                    "登录 (Enter)"
                }
            }) {
                setOnAction {
                    login()
                }

                enableWhen { loggingInProperty.not() }

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

                graphic = MaterialIconView(MaterialIcon.EXIT_TO_APP, "24")

            }
        }


    }
}
