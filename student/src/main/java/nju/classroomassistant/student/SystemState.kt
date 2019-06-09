package nju.classroomassistant.student

import nju.classroomassistant.student.network.SocketClient

// 记录系统状态，需要改写成观察者模式，允许Activity那边注册变化
object SystemState {
    var socket: SocketClient? = null

    // 当前登录的学生ID
    var studentId: String = ""

    // 当前是否正在进行测试


    // 讨论是否正在进行


    // 实时通知是否已经开始


}