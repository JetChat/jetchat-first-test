
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import composables.TextChannel
import entities.DMChannel
import entities.Message
import entities.User

@Composable
@Preview
fun App() {
	val text = "Hello, World!"
	val user by remember { mutableStateOf(User("Ayfri#0000")) }
	val user2 by remember { mutableStateOf(User("Ayfri#0001")) }
	val channel by remember { mutableStateOf(DMChannel(user to user2)) }
	var message: Message
	
	for (i in 0..8000) {
		message = Message("$text $i", user)
		channel.messages[message.id] = message
	}
	
	TextChannel(channel)
}

fun main() = application {
	Window(onCloseRequest = ::exitApplication, state = rememberWindowState(WindowPlacement.Maximized)) {
		App()
	}
}
