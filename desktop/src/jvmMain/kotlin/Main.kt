import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.elijahsemyonov.ocarinatabseditor.common.App


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
