package nju.classroomassistant.teacher.network

import nju.classroomassistant.shared.messages.discussion.StudentSendDiscussionMessage
import tornadofx.observable
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

class StudentInfo(
        val studentId: String,
        val handler: ConnectionHandler,
        val nickname: String
)

class StudentMap : Observable() {

    private val NICKNAME_LIST = listOf("小黑", "小白", "小红", "小明", "小刚")

    private val infoMap = ConcurrentHashMap<String, StudentInfo>()

    val allStudents: Collection<StudentInfo>
        get() = infoMap.values

    fun login(studentId: String, handler: ConnectionHandler): StudentInfo {
        val info = StudentInfo(studentId, handler, NICKNAME_LIST[studentId.toInt() % 5])
        infoMap[studentId] = info

        setChanged()
        notifyObservers()

        return info
    }

    fun logout(studentId: String) {
        infoMap.remove(studentId)

        setChanged()
        notifyObservers()
    }

    fun getStudentById(studentId: String): StudentInfo? {
        return infoMap[studentId]
    }

}