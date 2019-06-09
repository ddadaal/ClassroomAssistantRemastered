package nju.classroomassistant.student.systemstate

import nju.classroomassistant.shared.messages.exercise.ExerciseType
import java.util.*
import kotlin.properties.Delegates

class ExerciseState: Observable() {

    // 练习类型，null就是目前没有练习
    var exerciseType by Delegates.observable<ExerciseType?>(null) { _, oldValue, newValue ->
        if (oldValue != newValue) {
            setChanged()
            notifyObservers()
        }

    }

}