package nju.classroomassistant.student.systemstate

import java.util.*
import kotlin.properties.Delegates


class RaiseQuestionState: Observable() {

    // 实时提醒是否已经开启
    var instantNotificationEnabled: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
        if (oldValue != newValue) {
            setChanged()
            notifyObservers()
        }
    }
}