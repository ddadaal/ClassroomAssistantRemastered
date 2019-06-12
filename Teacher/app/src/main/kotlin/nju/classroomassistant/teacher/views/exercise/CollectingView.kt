package nju.classroomassistant.teacher.views.exercise

import com.jfoenix.controls.JFXButton
import javafx.beans.property.SimpleDoubleProperty
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import kfoenix.jfxbutton
import kfoenix.jfxspinner
import nju.classroomassistant.teacher.views.common.MainCss
import tornadofx.*
import tornadofx.Stylesheet.Companion.button
import javax.swing.text.html.StyleSheet




class CollectingView : View("Collecting View") {

    val progressProperty = SimpleDoubleProperty(0.8)
    val controller: ExerciseController by inject()




    override val root = borderpane {
        importStylesheet("/css/jfoenix-main-demo.css")
            center= vbox{
                borderpane{
                    left=label("作答人数")
                    right=label("160人")
                    style{
                        fontSize=16.px
                    }
                }

                jfxspinner() {
                    progress = progressProperty.value
                    setPrefSize(200.0, 200.0)
//                    addClass(MainCss.`blue-spinner`)
                }


                jfxbutton("结束",JFXButton.ButtonType.RAISED){
                    setOnAction {
                        controller.to<ResultView>()
                    }
                    prefWidth = 300.0
                    prefHeight = 30.0

                    style {
                        backgroundColor += c("#D63333")
                        textFill = Color.WHITE
                    }
                }

                spacing=50.0
                maxWidth = 300.0
                maxHeight = 300.0
            }
    }
}
