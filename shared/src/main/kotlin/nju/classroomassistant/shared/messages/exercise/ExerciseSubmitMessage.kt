package nju.classroomassistant.shared.messages.exercise

import nju.classroomassistant.shared.messages.Message


// 提交练习
class ExerciseSubmitMessage(
    val answer: ExerciseAnswer
): Message()