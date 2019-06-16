package nju.classroomassistant.teacher.views.home

import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment
import kfoenix.jfxspinner
import nju.classroomassistant.teacher.extensions.PageController
import nju.classroomassistant.teacher.views.question.DialogFragment
import tornadofx.*

class HomeView : View() {
    override val root = borderpane {
        top = stackpane() {
            label("人机交互")
            style {
                fontSize = 36.px
            }
        }
        center = borderpane() {
            paddingTop = 30
            top = vbox {
                jfxspinner() {
                    progress = 0.7
                    setPrefSize(150.0, 150.0)
                    addClass("no-text-spinner")
                }
                vbox {
                    label("140 / 200") {
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
                paddingTop = 30.0

            }

            bottom = gridpane() {
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
                    paddingTop = 40.0
                }

            }
        }

        paddingHorizontal = 50
    }

}

class HomeController : PageController(HomeView::class)