package nju.classroomassistant.teacher.views.common

import javafx.scene.paint.Color
import tornadofx.*

class MainCss: Stylesheet() {
    companion object {
        val exteriorLine by cssclass()
        val `blue-spinner` by cssclass()
        val arc by cssclass()
        val track by cssclass()
    }

    init {
        exteriorLine {
            borderColor += box(c("#1A237E"))
            borderWidth += box(1.px)
        }

        `blue-spinner` {
            arc {
                stroke = Color.RED
            }

            track {
                stroke = Color.BLACK
            }
        }

    }
}