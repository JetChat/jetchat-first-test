import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
	var text by remember { mutableStateOf("Hello, World!") }
	val user = User("Ayfri#0000")
	val message = Message(text, user)
	
	Message(message)
}

fun main() = application {
	Window(onCloseRequest = ::exitApplication) {
		App()
	}
}
