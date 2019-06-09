package nju.classroomassistant.shared.messages.raisequestion

import nju.classroomassistant.shared.messages.Message

class NotificationSettingChangeMessage(
    val instantNotification: Boolean
): Message()