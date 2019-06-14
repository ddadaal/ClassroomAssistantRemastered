package nju.classroomassistant.teacher.views.common

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXDialog
import com.jfoenix.controls.JFXDialogLayout
import com.jfoenix.effects.JFXDepthManager
import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import javafx.animation.Animation
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.beans.binding.Binding
import javafx.beans.property.SimpleObjectProperty
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.ContentDisplay
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BackgroundRepeat
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.util.Duration
import kfoenix.jfxbutton
import kfoenix.jfxsnackbar
import nju.classroomassistant.teacher.extensions.PageController
import nju.classroomassistant.teacher.extensions.makeDraggable
import nju.classroomassistant.teacher.extensions.makeResizeable
import nju.classroomassistant.teacher.network.GlobalVariables
import nju.classroomassistant.teacher.network.Server
import nju.classroomassistant.teacher.util.executeLater
import nju.classroomassistant.teacher.views.about.AboutViewController
import nju.classroomassistant.teacher.views.discussion.DiscussionController
import nju.classroomassistant.teacher.views.exercise.ExerciseController
import nju.classroomassistant.teacher.views.home.HomeController
import nju.classroomassistant.teacher.views.question.QuestionController
import tornadofx.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

enum class Page(val controller: KClass<out PageController>, val title: String) {
    HOME(HomeController::class, "主页"),
    QUESTION(QuestionController::class, "提问"),
    DISCUSSION(DiscussionController::class, "讨论"),
    EXERCISE(ExerciseController::class, "练习"),
    EXPORT(AboutViewController::class, "关于")
}

const val HEADER_HEIGHT = 64.0
const val CONTENT_MAX_WIDTH = 900.0


class MainView : View() {

    private val DEPTH = 3

    private val currentPageProperty = SimpleObjectProperty<Page>(Page.HOME)

    private var btnMaximize by singleAssign<JFXButton>()

    private var lbTime by singleAssign<Label>()

    private var rootPane by singleAssign<BorderPane>()

    private var closeDialog by singleAssign<JFXDialog>()

    fun navBinding(page: Page): Binding<Color?> {
        return currentPageProperty.objectBinding {
            if (it == page) {
                Color.WHITE
            } else {
                Color.GRAY
            }
        }
    }

    fun navItem(text: String, icon: MaterialIcon, targetPage: Page): JFXButton {
        return jfxbutton(text) {

            prefHeight = HEADER_HEIGHT
            paddingHorizontal = 32.0

            contentDisplay = ContentDisplay.TOP
            graphic = MaterialIconView(icon, "26").apply {
                fillProperty().bind(navBinding(targetPage))
            }

            textFillProperty().bind(navBinding(targetPage))

            setOnAction { currentPageProperty.set(targetPage) }
        }
    }

    fun navItem(text: String, icon: MaterialIcon, onClick: () -> Unit): JFXButton {
        return jfxbutton(text) {

            prefHeight = HEADER_HEIGHT
            paddingHorizontal = 32.0

            contentDisplay = ContentDisplay.TOP
            graphic = MaterialIconView(icon, "26").apply {
                fill = Color.GRAY
            }

            textFill = Color.GRAY

            setOnAction { onClick() }
        }
    }


    var contentPane: Parent by singleAssign()

    private fun exit() {
        export()
        Server.stop()
        primaryStage.close()
    }

    override val root = stackpane {

        closeDialog = JFXDialog()
        closeDialog.content = JFXDialogLayout().apply {
            heading += label("确定要导出课堂信息到桌面、下课并退出吗？") {
                font = Font("System Bold", 18.0)
            }
            actions += jfxbutton("取消") {

                ripplerFill = Color.ALICEBLUE

                setOnAction { closeDialog.close() }
                graphic = MaterialIconView(MaterialIcon.CLOSE, "20")
                font = Font(16.0)

            }
            actions += jfxbutton("确定") {

                ripplerFill = Color.RED

                addClass("dialog-accept")
                setOnAction { exit() }
                graphic = MaterialIconView(MaterialIcon.EXIT_TO_APP, "20")
                font = Font(16.0)

            }
        }


        this += closeDialog

        rootPane = borderpane {

            prefWidth = 700.0
            prefHeight = 700.0

            top = hbox {

                alignment = Pos.CENTER
                JFXDepthManager.setDepth(this, DEPTH)

                style {
                    backgroundColor += c("#232e5f")
                }

                borderpane {

                    maxWidth = CONTENT_MAX_WIDTH
                    prefHeight = HEADER_HEIGHT + 10

                    paddingVertical = 4.0

                    left = label(GlobalVariables.course.stringBinding { "当前课程：${it?.courseName}" }) {
                        graphic = imageview(resources.image("/img/2.jpg")) {
                            fitHeight = HEADER_HEIGHT
                            fitWidth = HEADER_HEIGHT * 2
                        }

                        contentDisplay = ContentDisplay.CENTER
                        textFill = c("#ffffff")
                    }

                    center = hbox {
                        alignment = Pos.CENTER
                        prefHeight = HEADER_HEIGHT

                        prefWidth = 100000.0

//                        spacing = 32.0

                        this += navItem("主页", MaterialIcon.HOME, Page.HOME)

                        this += navItem("提问", MaterialIcon.PAN_TOOL, Page.QUESTION)


                        this += navItem("讨论", MaterialIcon.COMMENT, Page.DISCUSSION)

                        this += navItem("练习", MaterialIcon.CHECK_CIRCLE, Page.EXERCISE)

                        this += navItem("导出课堂信息", MaterialIcon.IMPORT_EXPORT) {
                            export()
                        }


                    }

                    right = hbox {
                        alignment = Pos.CENTER

                        prefHeight = HEADER_HEIGHT

                        jfxbutton("") {
                            prefHeight = HEADER_HEIGHT

                            ripplerFill = Color.ALICEBLUE

                            paddingAll = 8.0

                            setOnMouseClicked { primaryStage.isIconified = true }

                            graphic = MaterialIconView(MaterialIcon.REMOVE).apply {
                                size = "26"
                                glyphStyle = "-fx-fill: #FFFFFF;"
                            }

                        }

                        btnMaximize = jfxbutton("") {
                            prefHeight = HEADER_HEIGHT
                            paddingAll = 8.0

                            ripplerFill = Color.ALICEBLUE
                            setOnMouseClicked { primaryStage.isMaximized = !primaryStage.isMaximized }

                            graphic = MaterialIconView(MaterialIcon.REMOVE).apply {
                                size = "26"
                                glyphStyle = "-fx-fill: #FFFFFF;"
                            }

                        }

                        jfxbutton("") {
                            prefHeight = HEADER_HEIGHT
                            paddingAll = 8.0

                            ripplerFill = Color.RED
                            setOnMouseClicked {
                                showDialog()
                            }

                            graphic = MaterialIconView(MaterialIcon.CLOSE).apply {
                                size = "26"
                                glyphStyle = "-fx-fill: #FFFFFF;"
                            }

                        }
                    }
                }



                center = anchorpane {

                    hbox {

                        alignment = Pos.CENTER



                        AnchorPane.setTopAnchor(this, 16.0)
                        AnchorPane.setRightAnchor(this, 16.0)
                        AnchorPane.setBottomAnchor(this, 16.0)
                        AnchorPane.setLeftAnchor(this, 16.0)

                        contentPane = vbox {

                            JFXDepthManager.setDepth(this, DEPTH)

                            this += find(find(Page.HOME.controller).view)

                            alignment = Pos.CENTER

                            maxWidth = CONTENT_MAX_WIDTH
                            prefWidth = CONTENT_MAX_WIDTH

                            style {
                                backgroundColor += c("#ffffff")
                            }
                        }


                    }

                }

                bottom = hbox {

                    JFXDepthManager.setDepth(this, DEPTH)

                    alignment = Pos.CENTER

                    style {
                        backgroundColor += c("#ffffff")
                    }

                    borderpane {
                        maxHeight = 28.0
                        paddingAll = 8.0

                        maxWidth = CONTENT_MAX_WIDTH
                        prefWidth = CONTENT_MAX_WIDTH

                        left = label("热键：Control H")

                        lbTime = label(GlobalVariables.currentTime.stringBinding { it?.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) })

                        right = lbTime
                    }
                }
            }
        }
    }

    private fun showDialog() {
        closeDialog.show(root)
    }


    override fun onDock() {
        primaryStage.makeDraggable(rootPane.top)

        primaryStage.makeResizeable()

        (btnMaximize.graphic as MaterialIconView).glyphNameProperty().bind(primaryStage.maximizedProperty().stringBinding {
            if (it == true) {
                MaterialIcon.KEYBOARD_ARROW_DOWN.name
            } else {
                MaterialIcon.KEYBOARD_ARROW_UP.name
            }
        })

        currentPageProperty.addListener { _, oldPage, newPage ->
            if (oldPage != newPage) {
                contentPane.childrenUnmodifiable[0].replaceWith(find(find(newPage.controller).view).root,
                        ViewTransition.Metro(0.2.seconds,
                                if (inFrontOf(oldPage, newPage)) {
                                    ViewTransition.Direction.LEFT
                                } else {
                                    ViewTransition.Direction.RIGHT
                                }
                        )
                )
            }

        }


//         TIME
        val timeline = Timeline(
                KeyFrame(Duration.millis(1000.0),
                        EventHandler { GlobalVariables.currentTime.set(LocalDateTime.now()) })
        )
        timeline.cycleCount = Animation.INDEFINITE
        timeline.play()

    }

    fun inFrontOf(page1: Page, page2: Page): Boolean {
        return Page.values().indexOf(page1) - Page.values().indexOf(page2) < 0
    }

    fun export() {
        val filename = "${GlobalVariables.course.get().courseName}-${LocalDateTime.now()}.xlsx"
        jfxsnackbar("已导出课堂信息到桌面：$filename", root)
    }
}
