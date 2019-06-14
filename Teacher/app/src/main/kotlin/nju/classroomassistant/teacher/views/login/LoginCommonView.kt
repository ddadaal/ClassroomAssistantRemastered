package nju.classroomassistant.teacher.views.login

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import nju.classroomassistant.teacher.extensions.makeDraggable
import nju.classroomassistant.teacher.extensions.makeResizeable
import nju.classroomassistant.teacher.network.GlobalVariables
import nju.classroomassistant.teacher.views.common.MainView
import tornadofx.*
import kotlin.reflect.KClass


enum class LoginRelatedPage(val viewClass: KClass<out View>) {
    LOGIN(LoginView::class),
    IMPORT(ImportFromJwView::class),
    COURSE_SELECTION(CourseSelectionView::class)
}

class LoginCommonView: View() {

    var rootPane: VBox by singleAssign()

    var current: Parent = findView(LoginRelatedPage.LOGIN)

    private fun findView(view: LoginRelatedPage): Parent {

        return find(view.viewClass, mapOf(
                "switchTo" to this::switch,
                "toMain" to this::toMain
        )).root
    }

    fun switch(view: LoginRelatedPage, direction: ViewTransition.Direction) {
        val target = findView(view)


        current.replaceWith(target, ViewTransition.Metro(0.3.seconds, direction), sizeToScene = true, centerOnScreen = true)
        current = target
    }

    fun toMain() {
        replaceWith<MainView>(sizeToScene = true, centerOnScreen = true)

        // set teacher notification initial value
        GlobalVariables.questionSession.isNotificationOpen.set(GlobalVariables.teacherInfo.get().questionNotification)

    }

    override fun onDock() {
        primaryStage.makeDraggable(rootPane)
    }

    override val root = stackpane {


        setMinSize(0.0, 0.0)

        prefHeight = 700.0

        style {
            borderColor += box(c("#1A237E"))
            borderWidth += box(1.px)
            backgroundImage += resources.url("/img/login_background.png").toURI()
        }

        rootPane = vbox {

            setMinSize(0.0, 0.0)

            StackPane.setMargin(this, Insets(10.0, 10.0, 10.0, 10.0))

            padding = Insets(5.0, 10.0, 5.0, 10.0)

            vbox {
                alignment = Pos.CENTER

                prefHeight = 60.0
                prefWidth = 300.0

                label("Classroom") {
                    font = Font("System Bold", 42.0)
                }

                label("Assistant") {
                    font = Font(18.0)
                    VBox.setMargin(this, Insets(0.0, 0.0, 0.0, 120.0))
                }

                padding = Insets(0.0, 0.0, 20.0, 0.0)

            }

            this += this@LoginCommonView.current
        }
    }


}