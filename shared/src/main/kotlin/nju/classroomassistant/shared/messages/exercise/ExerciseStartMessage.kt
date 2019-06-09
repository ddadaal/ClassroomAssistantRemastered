package nju.classroomassistant.shared.messages.exercise

import nju.classroomassistant.shared.messages.Message
import java.io.Serializable


// 开始练习

class ExerciseStartMessage(
    val exerciseType: ExerciseType
): Message()