package nju.classroomassistant.teacher.views.home

import javafx.geometry.Pos
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment
import kfoenix.jfxspinner
import nju.classroomassistant.teacher.extensions.PageController
import nju.classroomassistant.teacher.network.GlobalVariables
import tornadofx.*

class HomeView : View() {
    override val root = borderpane {
        top = stackpane {
            label(GlobalVariables.course.stringBinding { it?.courseName })
            style {
                fontSize = 36.px
            }
        }
        center = borderpane() {
            paddingTop = 30
            top = vbox {
                jfxspinner() {
                    progress = 0.4
                    setPrefSize(150.0, 150.0)
                    addClass("no-text-spinner")
                }
                label("出勤:  80人"){
                    font= Font.font(14.0)
                    paddingTop= -170.0
                }
                spacing=10.0
                alignment=Pos.CENTER
                paddingTop=30.0

            }
            bottom = vbox {
                spacing =20.0
                hbox {
                    label("教师") {
                        prefWidth = 80.0
                    }
                    label("冯桂焕")
                }
                hbox {
                    label("邮箱") {
                        prefWidth = 80.0
                    }
                    label("fenggh@nju.edu.cn")
                }
                hbox {
                    label("办公室") {
                        prefWidth = 80.0
                    }
                    label("费彝民楼926室")
                }
            }
        }

        paddingAll = 30
        paddingTop = 50
    }

}

class HomeController : PageController(HomeView::class)