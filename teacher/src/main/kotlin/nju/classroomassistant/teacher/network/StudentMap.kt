package nju.classroomassistant.teacher.network

import java.util.*
import java.util.concurrent.ConcurrentHashMap

class StudentInfo(
    val handler: ConnectionHandler
)

class StudentMap: Observable() {


    private val infoMap = ConcurrentHashMap<String, StudentInfo>()

    val allStudents: Collection<StudentInfo>
        get() = infoMap.values

    fun login(studentId: String, handler: ConnectionHandler) {
        infoMap[studentId] = StudentInfo(handler)
        notifyObservers()
    }

    fun logout(studentId: String) {
        infoMap.remove(studentId)
        notifyObservers()
    }


}