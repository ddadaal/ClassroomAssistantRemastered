package nju.classroomassistant.teacher.views.question

import com.jfoenix.controls.JFXDialog
import com.jfoenix.controls.JFXDialogLayout
import com.jfoenix.effects.JFXDepthManager
import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import javafx.geometry.Insets
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Screen
import javafx.stage.StageStyle
import kfoenix.jfxbutton
import nju.classroomassistant.teacher.extensions.makeDraggable
import nju.classroomassistant.teacher.extensions.makeResizeable
import nju.classroomassistant.teacher.network.GlobalVariables
import nju.classroomassistant.teacher.network.session.QuestionItem
import tornadofx.*
import java.time.format.DateTimeFormatter
import javafx.stage.Screen.getPrimary
import nju.classroomassistant.teacher.extensions.shadowedstackpane


class DialogFragment : Fragment("My Fragment") {

    override fun onDock() {

        currentStage?.apply {


            makeDraggable(root)
            makeResizeable()
            scene?.fill = Color.TRANSPARENT

        }
    }

    val questionItem: QuestionItem by param()

    override val root = shadowedstackpane {

        this += JFXDialogLayout().apply {

            style {
                background = Background(BackgroundFill(Color.rgb(255, 255, 255, 1.0), null, null))
            }

            JFXDepthManager.setDepth(this, 4)

            setHeading(label("${questionItem.studentNickname} 于 ${questionItem.simpleTime} 提出了一个问题："))

            setBody(label(questionItem.content))

            setActions(
                    jfxbutton("关闭并删除问题") {
                        action {
                            GlobalVariables.questionSession.questionList.remove(questionItem)
                            close()
                        }

                        graphic = MaterialIconView(MaterialIcon.REMOVE, "20")
                        font = Font(16.0)
                    },
                    jfxbutton("仅关闭") {
                        action {
                            close()
                        }

                        graphic = MaterialIconView(MaterialIcon.CHECK, "20")
                        font = Font(16.0)
                    })

        }
    }
}
