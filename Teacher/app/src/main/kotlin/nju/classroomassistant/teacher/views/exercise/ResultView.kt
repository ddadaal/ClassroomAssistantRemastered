package nju.classroomassistant.teacher.views.exercise

import com.jfoenix.controls.JFXButton
import com.jfoenix.svg.SVGGlyph
import com.jfoenix.svg.SVGGlyphLoader
import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import javafx.beans.binding.Bindings
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.paint.Color
import javafx.scene.text.Font
import kfoenix.jfxbutton
import tornadofx.*
import java.util.concurrent.Callable
import kotlin.reflect.KCallable

class ResultView : View("My View") {
    val resultMap = mapOf<String, Number>(
            Pair("192.168.1.7", 1),
            Pair("192.168.1.6", 1),
            Pair("192.168.1.5", 1),
            Pair("192.168.0.1", 12),
            Pair("127.0.0.1", 5),
            Pair("192.168.10.3", 20),
            Pair("这是一个超级长长长长长长长长长长长长\n长长长长长长长长长长长长的答案", 1),
            Pair("192.168.1.3", 1)

    )

    val controller: ExerciseController by inject()

    override val root = borderpane {
        center = barchart("练习结果", NumberAxis(), CategoryAxis()) {
            series("") {
                resultMap.forEach {
                    data(it.value, it.key)
                }
            }
            legendVisibleProperty().set(false)
            horizontalGridLinesVisibleProperty().set(false)
            horizontalZeroLineVisibleProperty().set(false)

            style {
                //                tickLabelFont = Font.font(14.0)
//                tickLength=0.px
            }

        }

        bottom = borderpane {
            left =  hbox{
                label("其他答案")
                jfxbutton( graphic=MaterialIconView(MaterialIcon.REFRESH, "20"))
                label("0.0.0.0"){
                    style{
                        paddingLeft = 20
                    }
                }
                alignment = Pos.CENTER_LEFT
            }
            right = jfxbutton("发布新练习", JFXButton.ButtonType.RAISED) {

                setOnAction {
                    controller.to<ExerciseInitView>()
                }
                style {
                    backgroundColor += c("#5264AE")
                    textFill = Color.WHITE
                    fontSize = 12.px
                }
            }
        }

        paddingAll = 20
    }
}
