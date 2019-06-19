package nju.classroomassistant.teacher.views.home

import javafx.beans.binding.Bindings
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment
import kfoenix.jfxspinner
import nju.classroomassistant.teacher.extensions.PageController
import nju.classroomassistant.teacher.extensions.asLargeAsPossible
import nju.classroomassistant.teacher.network.GlobalVariables
import nju.classroomassistant.teacher.network.Server
import nju.classroomassistant.teacher.views.question.DialogFragment
import tornadofx.*
import java.util.concurrent.Callable

class HomeView : View() {
    override val root = stackpane {
        //        maxWidth = 400.0
//        maxHeight = 400.0

        asLargeAsPossible()
        vbox {

            alignment = Pos.CENTER
            spacing = 20.0

            stackpane() {
                label("人机交互")
                style {
                    fontSize = 36.px
                }
            }


            separator {
            }

            vbox {

                jfxspinner {
                    progressProperty().bind(
                            Bindings.createDoubleBinding(Callable {
                                Server.studentMap.size / GlobalVariables.course.get().studentList.size.toDouble()
                            }, Server.studentMap.studentMapObservable, GlobalVariables.course))
                    setPrefSize(150.0, 150.0)
                    addClass("no-text-spinner")
                }
                vbox {
                    label(Bindings.createStringBinding(Callable {
                        "${Server.studentMap.size} / ${GlobalVariables.course.get().studentList.size}"
                    }, Server.studentMap.studentMapObservable, GlobalVariables.course)) {
                        font = Font.font(16.0)
                    }
                    label("出勤") {
                        font = Font.font(14.0)
                        paddingTop = 8
                    }
                    alignment = Pos.CENTER
                    paddingTop = -170.0
                }
                spacing = 10.0
                alignment = Pos.CENTER

            }

            separator {

            }

            gridpane() {
                text("教师").gridpaneConstraints {
                    columnRowIndex(0, 0)
                }
                label("冯桂焕").gridpaneConstraints {
                    columnRowIndex(1, 0)
                }
                text("邮箱").gridpaneConstraints {
                    columnRowIndex(0, 1)
                }
                text("fenggh@nju.edu.cn").gridpaneConstraints {
                    columnRowIndex(1, 1)
                }
                text("办公室").gridpaneConstraints {
                    columnRowIndex(0, 2)
                }
                text("费彝民楼926").gridpaneConstraints {
                    columnRowIndex(1, 2)
                }

                constraintsForColumn(0).prefWidth = 80.0
                for (i in 0 until 3) {
                    constraintsForRow(i).prefHeight = 40.0
                }
                gridpaneColumnConstraints {

                }


                style {
                    fontSize = 16.px
                    alignment = Pos.CENTER
                    translateX = 35.px
                }


            }
        }


//        paddingHorizontal = 50
    }

}

class HomeController : PageController(HomeView::class)