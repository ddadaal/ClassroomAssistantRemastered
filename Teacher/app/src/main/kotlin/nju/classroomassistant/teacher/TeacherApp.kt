package nju.classroomassistant.teacher

import javafx.stage.Stage
import javafx.stage.StageStyle
import nju.classroomassistant.teacher.network.Server
import nju.classroomassistant.teacher.views.common.MainCss
import nju.classroomassistant.teacher.views.login.CourseSelectionView
import nju.classroomassistant.teacher.views.login.LoginCommonView
import nju.classroomassistant.teacher.views.login.LoginView
import tornadofx.*

class TeacherApp: App(LoginCommonView::class, MainCss::class){
    init {
        reloadStylesheetsOnFocus()
    }

    override fun start(stage: Stage) {
        stage.initStyle(StageStyle.UNDECORATED)

        super.start(stage)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            launch<TeacherApp>(args)
        }

    }
}

