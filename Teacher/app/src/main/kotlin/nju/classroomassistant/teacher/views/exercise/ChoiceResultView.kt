package nju.classroomassistant.teacher.views.exercise

import com.jfoenix.controls.JFXButton
import com.jfoenix.svg.SVGGlyph
import com.jfoenix.svg.SVGGlyphLoader
import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import javafx.beans.binding.Bindings
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.paint.Color
import javafx.scene.text.Font
import kfoenix.jfxbutton
import nju.classroomassistant.shared.messages.exercise.type.ChoiceExerciseType
import nju.classroomassistant.teacher.extensions.asLargeAsPossible
import nju.classroomassistant.teacher.network.GlobalVariables
import tornadofx.*
import java.util.concurrent.Callable
import kotlin.reflect.KCallable

class ChoiceResultView : View("My View") {


    val data = FXCollections.observableArrayList<XYChart.Data<Number, String>>()

    val controller: ExerciseController by inject()

    override fun onDock() {
        val numbers = arrayListOf(8, 4, 2, 20, 1, 5)

        GlobalVariables.exerciseSession.exercise?.let {
            with (it as ChoiceExerciseType) {
                data.clear()

                for(i in it.optionsCount-1 downTo 0){
                    data.add(XYChart.Data(numbers[i],('A'+i)+""))
                }
            }
        }
    }

    override val root = borderpane {
        importStylesheet("/css/main.css")

        asLargeAsPossible()

        center = barchart("练习结果", NumberAxis(), CategoryAxis()) {
            series("", this@ChoiceResultView.data)

            legendVisibleProperty().set(false)
            horizontalGridLinesVisibleProperty().set(false)
            horizontalZeroLineVisibleProperty().set(false)
            barGap = 30.0
            yAxis.paddingRight=8.0
            addClass("choice-bar")
            animated=false
            style {
                tickLabelFont = Font.font(14.0)
            }

        }

        bottom = borderpane {
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

class ChoiceResultStyle: Stylesheet(){
    companion object{
        val chartBar by cssclass()
    }

    init{
        chartBar{
            translateY=30.px
        }
    }
}
