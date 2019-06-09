package nju.classroomassistant.shared.messages.raisequestion

import nju.classroomassistant.shared.messages.Message

// 服务器到客户端，实时提醒状态改变

class NotificationSettingChangeMessage(
    val instantNotificationEnabled: Boolean
): Message()