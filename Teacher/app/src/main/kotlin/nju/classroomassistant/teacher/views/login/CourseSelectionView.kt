package nju.classroomassistant.teacher.views.login

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTreeTableColumn
import com.jfoenix.controls.JFXTreeTableView
import com.jfoenix.controls.RecursiveTreeItem
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject
import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import javafx.scene.text.Font
import kfoenix.jfxbutton
import nju.classroomassistant.teacher.models.CourseInfo
import nju.classroomassistant.teacher.network.GlobalVariables
import nju.classroomassistant.teacher.network.Server
import nju.classroomassistant.teacher.repository.CourseInfoRepository
import tornadofx.*


class CourseInfoObservable(val courseInfo: CourseInfo) : RecursiveTreeObject<CourseInfoObservable>() {
    val courseName = SimpleStringProperty(courseInfo.courseName)
    val time = SimpleStringProperty(courseInfo.time)
}

class CourseSelectionView : View("选择课程") {

    val switchTo: (page: LoginRelatedPage, direction: ViewTransition.Direction) -> Unit by param()
    val toMain: () -> Unit by param()


    val courses = FXCollections.observableArrayList<CourseInfoObservable>()!!




    private val nameColumn = JFXTreeTableColumn<CourseInfoObservable, String>("课程名")
    private val timeColumn = JFXTreeTableColumn<CourseInfoObservable, String>("时间")

    private val table = JFXTreeTableView<CourseInfoObservable>()

    private val selectedCourseInfo = SimpleObjectProperty<CourseInfoObservable>()

    override fun onDock() {

        // initialize table


        nameColumn.setCellValueFactory { it.value.value.courseName }
        nameColumn.prefWidth = 170.0
        timeColumn.setCellValueFactory { it.value.value.time }
        timeColumn.prefWidth = 170.0


        table.isShowRoot = false
        table.isEditable = false
        table.root = RecursiveTreeItem<CourseInfoObservable>(courses) { it.children }
        table.columns.setAll(nameColumn, timeColumn)

        table.bindSelected(selectedCourseInfo)
        GlobalVariables.course.bind(selectedCourseInfo.objectBinding { it?.courseInfo })

        table.requestFocus()
        table.setOnKeyPressed { e ->
            if (e.code == KeyCode.ENTER) {
                selectedCourseInfo.get()?.let {
                    select()
                }
            } else if (e.code == KeyCode.ESCAPE) {
                switchTo(LoginRelatedPage.LOGIN, ViewTransition.Direction.RIGHT)

            }

        }

        // get course info
        courses.clear()
        CourseInfoRepository.data.computeIfAbsent(GlobalVariables.teacherId.get()) { arrayListOf() }.forEach {
            courses.add(CourseInfoObservable(it))
        }

        if (courses.isNotEmpty()) {
            table.selectFirst()
        }

    }


    private fun select() {
        // start server
        Server.start()
        toMain()
    }

    override val root = vbox {
        spacing = 30.0
        prefHeight = 1000.0

        alignment = Pos.TOP_CENTER

        label(GlobalVariables.teacherId.stringBinding { "欢迎，$it" }) {
            font = Font(28.0)
        }

        // initialize table

        vbox {

            prefHeight = 300.0

            this += table
        }

        vbox {
            spacing = 20.0

            jfxbutton("选择") {
                setOnAction {
                    select()
                }

                enableWhen { selectedCourseInfo.isNotNull }

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


            jfxbutton("从教务网导入课程") {
                setOnAction {
                    switchTo(LoginRelatedPage.IMPORT, ViewTransition.Direction.LEFT)
                }

//                enableWhen { loggingInProperty.not() }

                prefHeight = 32.0
                prefWidth = 340.0
                style {
                    backgroundColor += c("#3F51B5")
                    textFill = c("#FFFFFF")
                }

                graphic = MaterialIconView(MaterialIcon.IMPORT_EXPORT, "24").apply {
                    fill = c("#FFFFFF")
                }
            }

            jfxbutton("返回登录", JFXButton.ButtonType.RAISED) {
                setOnAction {
                    switchTo(LoginRelatedPage.LOGIN, ViewTransition.Direction.RIGHT)
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
}
