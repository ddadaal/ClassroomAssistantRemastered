package nju.classroomassistant.teacher.models

import java.io.Serializable
import java.time.temporal.WeekFields

data class CourseInfo(
        val courseName: String,
        val time: String,
        val studentList: List<StudentInfo>
): Serializable
