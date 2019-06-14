package nju.classroomassistant.teacher.views.common

import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class MainCss : Stylesheet() {
    companion object {
        val exteriorLine by cssclass()
        val chartBar by cssclass()
        val noTextSpinner by cssclass()
        val `icons-badge` by cssclass()
        val `badge-pane` by cssclass()
    }

    init {
        exteriorLine {
            borderColor += box(c("#1A237E"))
            borderWidth += box(1.px)
        }

        chartBar {
            barFill = c("#5264AE")
        }

        noTextSpinner{
            text{
                visibility=FXVisibility.HIDDEN
            }
        }

        `icons-badge` {
            `badge-pane` {
                backgroundColor += c("#ff4081")
                backgroundRadius += box(23.px)
                prefWidth = 23.px
                prefHeight = 23.px
                alignment = Pos.CENTER
            }

            label {
                fontWeight = FontWeight.BOLD
                fontSize = 14.px
                textFill = Color.WHITE
            }
        }
    }
}