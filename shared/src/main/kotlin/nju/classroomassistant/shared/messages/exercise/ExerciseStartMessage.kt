package nju.classroomassistant.shared.messages.exercise

import nju.classroomassistant.shared.messages.Message
import java.io.Serializable

class ExerciseStartMessage(
    val exerciseType: ExerciseType
): Message()