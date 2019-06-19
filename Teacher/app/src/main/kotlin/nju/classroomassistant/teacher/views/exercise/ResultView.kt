package nju.classroomassistant.teacher.views.exercise

import com.jfoenix.controls.JFXButton
import com.jfoenix.svg.SVGGlyph
import com.jfoenix.svg.SVGGlyphLoader
import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.paint.Color
import javafx.scene.text.Font
import kfoenix.jfxbutton
import nju.classroomassistant.teacher.extensions.asLargeAsPossible
import tornadofx.*
import java.util.concurrent.Callable
import kotlin.random.Random
import kotlin.reflect.KCallable

class ResultView : View("My View") {
    val resultMap = mapOf<String, Number>(
            " 3 " to 2,
            " 8 " to 4,
            " 6 " to 5,
            " 4 " to 10,
            " 5 " to 50
    )

    val controller: ExerciseController by inject()

    val otherAnswer = SimpleStringProperty("9")

    override val root = borderpane {

        asLargeAsPossible()

        center = barchart("练习结果", NumberAxis(), CategoryAxis()) {
            series("") {
                resultMap.forEach {
                    data(it.value, it.key)
                }
            }
            legendVisibleProperty().set(false)
            horizontalGridLinesVisibleProperty().set(false)
            horizontalZeroLineVisibleProperty().set(false)
            cssclass()

            style {
                                tickLabelFont = Font.font(14.0)
//                tickLength=0.px
            }

        }

        bottom = borderpane {
            left =  hbox{
                label("其他答案")
                jfxbutton( graphic=MaterialIconView(MaterialIcon.REFRESH, "20")) {
                    setOnMouseClicked {
                        otherAnswer.set("${Random.nextInt(10,20)}")
                    }
                }
                label(otherAnswer){
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
