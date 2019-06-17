package nju.classroomassistant.teacher.extensions

import javafx.event.EventTarget
import javafx.scene.Node
import javafx.scene.layout.*
import javafx.scene.paint.Color
import nju.classroomassistant.teacher.views.exercise.ExerciseController
import nju.classroomassistant.teacher.views.question.QuestionController
import tornadofx.*
import kotlin.reflect.KClass

abstract class PageController(initial: KClass<out View>) : Controller() {
    var view = initial

    inline fun <reified T : View> to() {
        find(view).replaceWith(T::class, ViewTransition.Metro(0.3.seconds))
        view = T::class
    }
}

fun Region.asLargeAsPossible() {
    setPrefSize(10000.0, 10000.0)
}

fun EventTarget.shadowedstackpane(op: StackPane.() -> Unit): StackPane {
    return stackpane {

        paddingAll = 12.0


        background = Background(BackgroundFill(Color.WHITE, CornerRadii(2.0), null))

        style {
            backgroundColor += Color.TRANSPARENT
        }

        op(this)
    }
}