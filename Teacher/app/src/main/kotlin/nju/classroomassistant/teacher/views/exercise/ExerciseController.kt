package nju.classroomassistant.teacher.views.exercise

import nju.classroomassistant.teacher.extensions.PageController

enum class ExerciseType{
    CHOICE,
    BLANK
}


class ExerciseController: PageController(ExerciseInitView::class){
    var type:ExerciseType = ExerciseType.BLANK
}