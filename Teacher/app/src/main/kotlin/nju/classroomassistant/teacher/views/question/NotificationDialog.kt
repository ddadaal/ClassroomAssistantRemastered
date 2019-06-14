package nju.classroomassistant.teacher.views.question

import com.jfoenix.controls.JFXDialogLayout
import com.jfoenix.effects.JFXDepthManager
import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import javafx.scene.Parent
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
import nju.classroomassistant.teacher.network.session.QuestionItem
import nju.classroomassistant.teacher.util.executeLater
import tornadofx.*
import java.time.format.DateTimeFormatter

class NotificationDialog: Fragment() {
    override fun onDock() {

        // show at the right top corner
        val primaryScreenBounds = Screen.getPrimary().visualBounds

        currentStage?.apply {
            isAlwaysOnTop = true
            y = 0.0
            x = primaryScreenBounds.width - (scene?.width ?: 500.0)
            makeDraggable(root)
            makeResizeable()
            scene?.fill = Color.TRANSPARENT

        }


        executeLater(5000) {
            close()
        }

    }

    val questionItem: QuestionItem by param()

    override val root = stackpane {

        paddingAll = 12.0

//        opacity = 0.1

        background = Background(BackgroundFill(Color.WHITE, CornerRadii(2.0), null))

        style {
            backgroundColor += Color.TRANSPARENT
        }

        this += JFXDialogLayout().apply {

            style {
                background = Background(BackgroundFill(Color.rgb(255, 255, 255, 1.0), null, null))
            }

            JFXDepthManager.setDepth(this, 4)

            setHeading(label("${questionItem.studentNickname} 于 ${questionItem.time.format(DateTimeFormatter.ofPattern("HH:mm:ss"))} 提出了一个问题："))

            setBody(label(questionItem.abstract))

            setActions(
                    jfxbutton("关闭") {
                        action {
                            close()
                        }

                        graphic = MaterialIconView(MaterialIcon.REMOVE, "20")
                        font = Font(16.0)
                    },
                    jfxbutton("查看详细") {
                        action {
                            find<DialogFragment>(DialogFragment::questionItem to questionItem).openWindow(StageStyle.TRANSPARENT)
                            close()
                        }

                        graphic = MaterialIconView(MaterialIcon.CHECK, "20")
                        font = Font(16.0)
                    })

        }
    }

}