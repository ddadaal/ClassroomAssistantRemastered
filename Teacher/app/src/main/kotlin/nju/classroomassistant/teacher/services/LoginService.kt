package nju.classroomassistant.teacher.services

import nju.classroomassistant.teacher.models.CourseInfo
import nju.classroomassistant.teacher.models.StudentInfo

object LoginService {

    private val students = listOf(
            StudentInfo("陈俊宇", "161250011"),
            StudentInfo("陈俊达", "161250010"),
            StudentInfo("廖均达", "161250068"),
            StudentInfo("訾源", "161250220")
    )

    fun importFromJw(id: String, password: String): List<CourseInfo> {
        return listOf(
                CourseInfo("软件测试", "星期一，星期五", students),
                CourseInfo("人机交互", "星期二", students),
                CourseInfo("实证软件工程", "星期二，星期四", students),
                CourseInfo("移动互联网", "星期三", students)
        )

    }
}