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
	val channel by remember { mutableStateOf(GuildTextChannel("test")) }
	val user by remember { mutableStateOf(User("Ayfri#0000")) }
	var message by remember { mutableStateOf(Message("$text 0", user)) }
	
	for (i in 0..20) {
		message = Message("$text $i", user)
		channel.messages[message.id] = message
	}
	
	MessageList(channel)
}

fun main() = application {
	Window(onCloseRequest = ::exitApplication) {
		App()
	}
}
