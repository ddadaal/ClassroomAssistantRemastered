package nju.classroomassistant.teacher.network

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import nju.classroomassistant.shared.messages.discussion.DiscussionEndMessage
import nju.classroomassistant.shared.messages.discussion.DiscussionStartMessage
import nju.classroomassistant.shared.messages.discussion.StudentSendDiscussionMessage
import nju.classroomassistant.shared.messages.exercise.answer.ExerciseAnswer
import nju.classroomassistant.shared.messages.exercise.type.ExerciseType
import nju.classroomassistant.teacher.models.CourseInfo
import nju.classroomassistant.teacher.network.session.DiscussionSession
import nju.classroomassistant.teacher.network.session.ExerciseSession
import nju.classroomassistant.teacher.network.session.QuestionSession
import tornadofx.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

/**
 * Author: J.D. Liao
 * Date: 2019-06-10
 * Description:
 */
object GlobalVariables {

    // 登录信息
    val teacherId = SimpleStringProperty()
    val course = SimpleObjectProperty<CourseInfo>()

    // 练习信息
    val exerciseSession = ExerciseSession()

    // 讨论信息
    val discussionSession = DiscussionSession()

    // 提问信息
    val questionSession = QuestionSession()
}




