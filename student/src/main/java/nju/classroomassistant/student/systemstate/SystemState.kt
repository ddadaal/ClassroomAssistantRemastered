package nju.classroomassistant.student.systemstate

import nju.classroomassistant.student.network.SocketClient
import java.util.*

// 记录系统状态，需要改写成观察者模式，允许Activity那边注册变化
object SystemState {
    var socket: SocketClient? = null

    // 当前登录的学生ID
    var studentId: String = ""

    // 练习相关状态
    val exercise = ExerciseState()

    // 讨论的相关状态
    val discussion = DiscussionState()

    // 提问相关状态
    val raiseQuestion = RaiseQuestionState()
}