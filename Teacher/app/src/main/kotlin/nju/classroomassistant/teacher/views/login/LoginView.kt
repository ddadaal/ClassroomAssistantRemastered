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
import com.sun.javaws.ui.SplashScreen.hide
import java.util.Collections.addAll
import com.jfoenix.controls.JFXAutoCompletePopup
import nju.classroomassistant.shared.util.HistoryQueue
import nju.classroomassistant.teacher.network.GlobalVariables
import nju.classroomassistant.teacher.repository.TeacherIdHistoryRepository
import nju.classroomassistant.teacher.util.executeLater
import tornadofx.Stylesheet.Companion.textField


class LoginView : View("My View"), Observer, Logger {
    override fun update(o: Observable?, arg: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    val idValidator = RequiredFieldValidator().apply {
        message = "请输入教师ID"
    }

    private val idProperty = SimpleStringProperty("")
    private val loggingInProperty = SimpleBooleanProperty(false)

    var idField: JFXTextField by singleAssign()


    override fun onDock() {
        primaryStage.makeResizeable()
        primaryStage.makeDraggable(root)

        // initialize auto suggestion
        val autoCompletePopup = JFXAutoCompletePopup<String>()
        autoCompletePopup.suggestions.addAll(TeacherIdHistoryRepository.data.history.reversed())

        autoCompletePopup.setSelectionHandler { event ->
            idProperty.set(event.`object`)

            // you can do other actions here when text completed
        }


        // filtering options
        idProperty.addListener { _, old, newValue ->
            autoCompletePopup.filter { string -> string.toLowerCase().contains(newValue) }
            if (autoCompletePopup.filteredSuggestions.isEmpty()) {
                autoCompletePopup.hide()
            } else {
                autoCompletePopup.show(idField)
            }
        }


        idField.setOnMouseClicked {
            autoCompletePopup.filter { true }
            autoCompletePopup.show(idField)
        }

    }



    fun login() {

        if (!idField.validate()) {
            return
        }

        loggingInProperty.set(true)

        // 记录输入历史
        TeacherIdHistoryRepository.data.add(idProperty.get())
        TeacherIdHistoryRepository.save()

        // 强行停1s

        executeLater(500) {
            loggingInProperty.set(false)

            // 登录，设置全局变量，跳到课程选择界面
            GlobalVariables.teacherId.set(idProperty.get())
            view.switch(find(CourseSelectionView::class).customView)
        }


    }

    val customView = vbox {
        alignment = Pos.BOTTOM_CENTER

//            prefHeight = 80.0
//            prefWidth = 40.0

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

                enableWhen { loggingInProperty.not() }

                setOnKeyPressed {
                    if (it.code == KeyCode.ENTER) {
                        login()
                    }
                }
            }
        }

        vbox {
            spacing = 20.0

            paddingTop = 80.0

            jfxbutton(loggingInProperty.stringBinding { if (it == true) { "登录中" } else { "登录"} }) {
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

    private val view = find<LoginCommonView>(mapOf(
            LoginCommonView::content to customView
    ))

    override val root = view.root
}
