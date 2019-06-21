package nju.classroomassistant.teacher.views.common

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class MainCss : Stylesheet() {
    companion object {
        val exteriorLine by cssclass()
        val chartBar by cssclass()
        val noTextSpinner by cssclass()
        val homeTable by cssclass()
        val `icons-badge` by cssclass()
        val `badge-pane` by cssclass()
        val `list-view` by cssclass()
        val `scroll-bar` by cssclass()
        val `horizontal` by csspseudoclass()
        val blueSpinner by cssclass()
        val arc by cssclass()
        val thickArc by cssclass()

    }

    init {
        exteriorLine {
            borderColor += box(c("#1A237E"))
            borderWidth += box(1.px)
        }

        chartBar {
            barFill = c("#5264AE")
        }

        noTextSpinner {
            text {
                visibility = FXVisibility.HIDDEN
            }
        }
        homeTable {

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

        `list-view` {
            `scroll-bar` and horizontal {
                incrementArrow {
                    padding = box(0.px)
                }

                decrementArrow {
                    padding = box(0.px)
                }

                incrementButton {
                    padding = box(0.px)
                }

                decrementButton {
                    padding = box(0.px)
                }

            }
        }

        blueSpinner{
            arc{
                stroke=c("#5264AE")
            }
        }

        thickArc{
            arc{
                strokeWidth =14.px
            }
        }

//                .list-view .scroll-bar:horizontal .increment-arrow,
//        .list-view .scroll-bar:horizontal .decrement-arrow,
//        .list-view .scroll-bar:horizontal .increment-button,
//        .list-view .scroll-bar:horizontal .decrement-button {
//            -fx-padding:0;
//        }




    }
}