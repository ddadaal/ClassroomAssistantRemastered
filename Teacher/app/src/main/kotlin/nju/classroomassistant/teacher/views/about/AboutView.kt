package nju.classroomassistant.teacher.views.about

import com.jfoenix.controls.JFXSnackbar
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import nju.classroomassistant.teacher.extensions.PageController
import tornadofx.*
import java.awt.Desktop
import java.awt.event.ActionEvent
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException

class AboutView: View() {
    override val root: VBox by fxml("/fxml/AboutPage.fxml")

    fun onRepositoryAddressClicked(mouseEvent: MouseEvent) {
        if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(URI("https://github.com/viccrubs/ClassroomAssistantRemastered"))

        }
    }

}

class AboutViewController: PageController(AboutView::class)