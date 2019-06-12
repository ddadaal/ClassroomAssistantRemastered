package nju.classroomassistant.teacher.views.common

import javafx.scene.paint.Color
import tornadofx.*

class MainCss : Stylesheet() {
    companion object {
        val exteriorLine by cssclass()
        val chartBar by cssclass()
    }

    init {
        exteriorLine {
            borderColor += box(c("#1A237E"))
            borderWidth += box(1.px)
        }

        chartBar {
            barFill = c("#5264AE")
        }

    }
}