package nju.classroomassistant.teacher.repository

import nju.classroomassistant.teacher.models.CourseInfo
import nju.classroomassistant.teacher.models.TeacherInfo

object TeacherInfoRepository: Repository<HashMap<String, TeacherInfo>> (
        "teacherInfoRepository",
        hashMapOf()
)