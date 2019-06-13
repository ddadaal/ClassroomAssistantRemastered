package nju.classroomassistant.teacher.views.question

import javafx.geometry.Insets
import javafx.scene.paint.Color
import kfoenix.jfxbutton
import nju.classroomassistant.teacher.extensions.makeDraggable
import tornadofx.*

class DialogFragment : Fragment("My Fragment") {
    override fun onDock() {

        currentStage?.makeDraggable(root)
    }
    override val root = borderpane() {

        top = label("孙悟空提出了一个问题：")
        center = vbox {
            label("老师，我有一个特别长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长的问题") {
                maxWidth = 300.0
                wrapTextProperty().set(true)
                padding = Insets(10.0,0.0,10.0,0.0)
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
