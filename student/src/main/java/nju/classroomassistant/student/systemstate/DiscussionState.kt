package nju.classroomassistant.student.systemstate

import java.util.*
import kotlin.properties.Delegates

class DiscussionState: Observable() {

    // 讨论是否已经开始
    var started: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
        if (oldValue != newValue) {
            setChanged()
            notifyObservers()
        }
    }

}