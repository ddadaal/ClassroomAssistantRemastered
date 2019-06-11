package nju.classroomassistant.teacher.views.home

import javafx.scene.Parent
import nju.classroomassistant.teacher.extensions.PageController
import tornadofx.*

class HomeView: View() {
    override val root = stackpane {
        label("HOME")
    }

}

class HomeController: PageController(HomeView::class)