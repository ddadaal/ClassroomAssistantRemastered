package nju.classroomassistant.teacher.network.session

import javafx.collections.FXCollections
import nju.classroomassistant.shared.messages.exercise.answer.ExerciseAnswer
import nju.classroomassistant.shared.messages.exercise.type.ExerciseType
import nju.classroomassistant.teacher.network.StudentInfo

class ExerciseSession {


    var exercise: ExerciseType? = null

    var started = false
        private set

    val answers = FXCollections.observableHashMap<StudentInfo, ExerciseAnswer>()!!

    fun start(exercise: ExerciseType) {
        started = true
        this.exercise = exercise
        answers.clear()
    }

    fun finish() { started = false }

    fun add(studentInfo: StudentInfo, answer: ExerciseAnswer) {
        answers[studentInfo] = answer
    }
}