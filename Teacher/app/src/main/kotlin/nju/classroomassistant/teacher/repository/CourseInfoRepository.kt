package nju.classroomassistant.teacher.repository

import nju.classroomassistant.teacher.models.CourseInfo

object CourseInfoRepository: Repository<HashMap<String, ArrayList<CourseInfo>>>(
        "courseInfoRepository",
        hashMapOf()
)