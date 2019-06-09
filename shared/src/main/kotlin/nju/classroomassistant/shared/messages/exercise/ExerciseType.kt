package nju.classroomassistant.shared.messages.exercise

import java.io.Serializable

open class ExerciseType: Serializable

class ChoiceExerciseType(
    val optionsCount: Int
): ExerciseType()

class FillBlankExerciseType: ExerciseType()

open class ExerciseAnswer: Serializable

class ChoiceExerciseAnswer(
    val answers: ArrayList<String>
): ExerciseAnswer()

class FillBlankExerciseAnswer(
    val answer: String
): ExerciseAnswer()