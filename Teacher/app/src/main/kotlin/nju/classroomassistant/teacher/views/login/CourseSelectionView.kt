package nju.classroomassistant.teacher.views.login

import com.jfoenix.controls.JFXButton
import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import javafx.scene.text.Font
import kfoenix.jfxbutton
import kfoenix.jfxtextfield
import nju.classroomassistant.teacher.network.GlobalVariables
import tornadofx.*

class CourseSelectionView: View() {


    private fun select() {

    }

    val customView = vbox {
        alignment = Pos.BOTTOM_CENTER

//            prefHeight = 80.0
//            prefWidth = 40.0
        spacing = 20.0

        label(GlobalVariables.teacherId.stringBinding { "欢迎，$it" }) {
            font = Font(28.0)
        }

        hbox {
        }

        vbox {
            spacing = 20.0

            jfxbutton("选择") {
                setOnAction {
                    select()
                }

//                enableWhen { loggingInProperty.not() }

                prefHeight = 32.0
                prefWidth = 340.0
                style {
                    backgroundColor += c("#3F51B5")
                    textFill = c("#FFFFFF")
                }

                graphic = MaterialIconView(MaterialIcon.CHECK, "24").apply {
                    fill = c("#FFFFFF")
                }
            }

            jfxbutton("返回登录", JFXButton.ButtonType.RAISED) {
                setOnAction {
                    view.switch(find(LoginView::class).customView)
                }
                prefHeight = 32.0
                prefWidth = 340.0

                style {
                    backgroundColor += c("E8EAF6")
                    textFill = c("#000000")
                }

                graphic = MaterialIconView(MaterialIcon.ARROW_BACK, "24")
            }

            jfxbutton("退出", JFXButton.ButtonType.RAISED) {
                setOnAction {
                    primaryStage.close()
                }
                prefHeight = 32.0
                prefWidth = 340.0

                style {
                    backgroundColor += c("E8EAF6")
                    textFill = c("#000000")
                }

                graphic = MaterialIconView(MaterialIcon.EXIT_TO_APP, "24")
            }
        }


    }


    private val view = find<LoginCommonView>(mapOf(
            LoginCommonView::content to vbox {
                alignment = Pos.CENTER

            }
    ))

    override val root = view.root
}