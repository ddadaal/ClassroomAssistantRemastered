package nju.classroomassistant.teacher.models

import java.io.Serializable

data class TeacherInfo(
        val teacherId: String,
        var teacherName: String,
        var questionNotification: Boolean,
        var courses: ArrayList<CourseInfo>
): Serializable