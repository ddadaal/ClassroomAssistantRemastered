package nju.classroomassistant.teacher.extension

import javafx.beans.property.SimpleObjectProperty
import javafx.event.EventTarget
import javafx.scene.Node
import nju.classroomassistant.teacher.views.exercise.ExerciseController
import nju.classroomassistant.teacher.views.question.QuestionController
import tornadofx.*
import kotlin.reflect.KClass

abstract class PageController(val initial: KClass<out View>): Controller() {
    var view = find(initial)

    inline fun <reified T: View> to() {
        view.replaceWith(T::class)
        view = find(T::class)
    }
}

enum class Page(val controller: KClass<out PageController>?,val title: String) {
    EXERCISE(ExerciseController::class,"练习"),
    QUESTION(QuestionController::class,"提问"),
    DISCUSSION(null,"讨论");
}

fun Node.switchTo(page: Page) {
    replaceWith(find(page.controller!!).view.root)
}