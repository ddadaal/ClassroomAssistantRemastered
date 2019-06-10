package nju.classroomassistant.teacher.views.login

import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import kfoenix.jfxtextfield
import nju.classroomassistant.teacher.extensions.makeDraggable
import nju.classroomassistant.teacher.extensions.makeResizeable
import tornadofx.*

class LoginCommonView: Fragment() {

    var rootPane: BorderPane by singleAssign()

    val center: Parent by param()

    val bottom: Parent by param()

    override fun onDock() {
//        primaryStage.makeResizeable()
        primaryStage.makeDraggable(rootPane)
    }

    override val root = stackpane {

        style {
            borderColor += box(c("#1A237E"))
            borderWidth += box(1.px)
        }

        rootPane = borderpane {

            StackPane.setMargin(this, Insets(10.0, 10.0, 10.0, 10.0))

            padding = Insets(5.0, 10.0, 5.0, 10.0)

            prefHeight = 396.0
            prefWidth = 356.0

            top = vbox {
                alignment = Pos.CENTER

                prefHeight = 60.0
                prefWidth = 300.0

                label("Classroom") {
                    font = Font("System Bold", 42.0)
                }

                label("Assistant") {
                    font = Font(18.0)
                    VBox.setMargin(this, Insets(0.0, 0.0, 0.0, 120.0))
                }

                padding = Insets(0.0, 0.0, 20.0, 0.0)

            }

            center = this@LoginCommonView.center

            bottom = this@LoginCommonView.bottom
        }
    }
}