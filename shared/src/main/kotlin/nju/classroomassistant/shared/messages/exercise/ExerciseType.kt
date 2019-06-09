package nju.classroomassistant.shared.messages.exercise

import java.io.Serializable

open class ExerciseType: Serializable

class ChoiceExerciseType(
    val optionsCount: Int
): ExerciseType()

class FillBlankExerciseType: ExerciseType()