package nju.classroomassistant.teacher.views.login

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTreeTableColumn
import com.jfoenix.controls.JFXTreeTableView
import com.jfoenix.controls.RecursiveTreeItem
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject
import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import javafx.scene.text.Font
import jdk.nashorn.internal.objects.Global
import kfoenix.jfxbutton
import kfoenix.jfxtextfield
import nju.classroomassistant.teacher.models.CourseInfo
import nju.classroomassistant.teacher.network.GlobalVariables
import nju.classroomassistant.teacher.repository.CourseInfoRepository
import tornadofx.*


class CourseInfoObservable(courseInfo: CourseInfo) : RecursiveTreeObject<CourseInfoObservable>() {
    val courseName = SimpleStringProperty(courseInfo.name)
    val time = SimpleStringProperty(courseInfo.time)
}

class CourseSelectionView : View("选择课程") {

    val switchTo: (page: LoginRelatedPage, direction: ViewTransition.Direction) -> Unit by param()
    val courses = FXCollections.observableArrayList<CourseInfoObservable>()



    val nameColumn = JFXTreeTableColumn<CourseInfoObservable, String>("课程名")
    val timeColumn = JFXTreeTableColumn<CourseInfoObservable, String>("时间")

    val table = JFXTreeTableView<CourseInfoObservable>()



    override fun onDock() {

        // initialize table

        nameColumn.setCellValueFactory { it.value.value.courseName }
        timeColumn.setCellValueFactory { it.value.value.time }

        table.isShowRoot = false
        table.isEditable = false
        table.root = RecursiveTreeItem<CourseInfoObservable>(courses) { it.children }
        table.columns.setAll(nameColumn, timeColumn)



        // get course info
        courses.clear()
        CourseInfoRepository.data.computeIfAbsent(GlobalVariables.teacherId.get()) { arrayListOf() }.forEach {
            courses.add(CourseInfoObservable(it))
        }
//

    }

    override val root = vbox {
        spacing = 20.0

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
