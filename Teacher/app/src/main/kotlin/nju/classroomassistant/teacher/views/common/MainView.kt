package nju.classroomassistant.teacher.views.common

import com.jfoenix.effects.JFXDepthManager
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import javafx.event.ActionEvent
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BackgroundRepeat
import javafx.scene.layout.GridPane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import nju.classroomassistant.teacher.TeacherApp
import nju.classroomassistant.teacher.extensions.PageController
import nju.classroomassistant.teacher.extensions.makeDraggable
import nju.classroomassistant.teacher.extensions.makeResizeable
import nju.classroomassistant.teacher.views.about.AboutViewController
import nju.classroomassistant.teacher.views.discussion.DiscussionController
import nju.classroomassistant.teacher.views.exercise.ExerciseController
import nju.classroomassistant.teacher.views.home.HomeController
import nju.classroomassistant.teacher.views.question.QuestionController
import tornadofx.*
import kotlin.reflect.KClass

class MainView : View() {

    private val DEPTH = 3

    override val root: StackPane by fxml("/fxml/MainFrame.fxml")

    val maximizeButtonGlyph: MaterialIconView by fxid()

    val contentPane: StackPane by fxid()
    val drawer: Parent by fxid()
    val titleBar: Parent by fxid()
    val bottomBar: Parent by fxid()

    lateinit var current: Node

    override fun onDock() {
        maximizeButtonGlyph.glyphNameProperty().bind(
                primaryStage.maximizedProperty().stringBinding {
                    if (it == true) {
                        "KEYBOARD_ARROW_DOWN"
                    } else {
                        "KEYBOARD_ARROW_UP"
                    }
                }
        )

        current = contentPane.childrenUnmodifiable[0]

        primaryStage.makeDraggable(titleBar)
        primaryStage.makeResizeable()

        JFXDepthManager.setDepth(contentPane, DEPTH)
        JFXDepthManager.setDepth(titleBar, DEPTH)
        JFXDepthManager.setDepth(drawer, DEPTH)
        JFXDepthManager.setDepth(bottomBar, DEPTH)
    }

    fun onLogButtonClicked(actionEvent: ActionEvent) {

    }

    enum class Page(val controller: KClass<out PageController>?, val title: String) {
        HOME(HomeController::class, "主界面"),
        EXERCISE(ExerciseController::class,"练习"),
        QUESTION(QuestionController::class,"提问"),
        DISCUSSION(DiscussionController::class,"讨论"),
        ABOUT(AboutViewController::class, "关于")
    }

    private fun to(page: Page) {
        val target = find(find(page.controller!!).view).root
        if (target != current) {
            current.replaceWith(target, ViewTransition.Metro(0.3.seconds), true, true)
            current = target

        }

    }

    fun onBtnHomeClicked(actionEvent: ActionEvent) {
        to(Page.HOME)
    }

    fun onBtnExerciseClicked(actionEvent: ActionEvent) {
        to(Page.EXERCISE)
    }

    fun onBtnRaiseQuestionClicked(actionEvent: ActionEvent) {
        to(Page.QUESTION)
    }

    fun onBtnDiscussionClicked(actionEvent: ActionEvent) {
        to(Page.DISCUSSION)
    }

    fun onBtnAboutClicked(actionEvent: ActionEvent) {
        to(Page.ABOUT)
    }

    fun onBtnMinimizeClicked(mouseEvent: MouseEvent) {
        primaryStage.isIconified = true
    }

    fun onBtnMaximizeClicked(mouseEvent: MouseEvent) {
        primaryStage.isMaximized = !primaryStage.isMaximized
    }

    fun onBtnCloseClicked(mouseEvent: MouseEvent) {
//        TeacherApp.exit()/

    }



}