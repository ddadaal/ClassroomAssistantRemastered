package nju.classroomassistant.teacher.repository

import nju.classroomassistant.shared.util.HistoryQueue

object TeacherIdHistoryRepository: Repository<HistoryQueue>(
        "teacherIdHistory",
        HistoryQueue().apply { add("161250010") }
)