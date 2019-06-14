package nju.classroomassistant.teacher.views.question

import javafx.geometry.Insets
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import kfoenix.jfxbutton
import nju.classroomassistant.teacher.extensions.makeDraggable
import tornadofx.*

class DialogFragment : Fragment("My Fragment") {

    val dialogTitle: String by param()

    val content: String by param()

    override fun onDock() {

        currentStage?.makeDraggable(root)
    }
    override val root = borderpane {

        setPrefSize(400.0, 300.0)

        top = label(dialogTitle)
        center = vbox {
            label(content) {
                maxWidth = 300.0
                wrapTextProperty().set(true)
                padding = Insets(10.0,0.0,10.0,0.0)

                style {
                    fontSize = 20.px
                    fontWeight = FontWeight.BOLD
                }
            }
        }
        bottom = borderpane(){
            right = jfxbutton("确定") {
                action {
                    close()
                }

                style {
                    backgroundColor += c("#5264AE")
                    textFill = Color.WHITE
                }
            }
        }
        paddingAll = 15
        maxWidth = 300.0

        style {
            borderRadius += box( 10.px)
            backgroundRadius += box( 10.px)

            backgroundColor += Color.WHITE
            borderColor += box(Color.GRAY)
        }
    }
}
