package nju.classroomassistant.teacher.network.session

import javafx.collections.FXCollections
import nju.classroomassistant.shared.messages.exercise.ExerciseEndMessage
import nju.classroomassistant.shared.messages.exercise.ExerciseStartMessage
import nju.classroomassistant.shared.messages.exercise.answer.ExerciseAnswer
import nju.classroomassistant.shared.messages.exercise.type.ExerciseType
import nju.classroomassistant.teacher.network.Server
import nju.classroomassistant.teacher.network.StudentInfo
import tornadofx.*

class ExerciseSession {


    var exercise: ExerciseType? = null

    var started = false
        private set

    val answers = FXCollections.observableHashMap<StudentInfo, ExerciseAnswer>()!!

    fun start(exercise: ExerciseType) {
        started = true
        this.exercise = exercise
        answers.clear()
        Server.writeToAllStudentsAsync(ExerciseStartMessage(exercise))
    }

    fun finish() {
        started = false
        Server.writeToAllStudentsAsync(ExerciseEndMessage())
    }

    fun add(studentInfo: StudentInfo, answer: ExerciseAnswer) {
        runLater {
            answers[studentInfo] = answer
        }
    }
}