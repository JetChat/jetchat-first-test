
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import composables.Guild
import entities.ChannelType
import entities.Guild
import entities.Message
import entities.User

@Composable
@Preview
fun App() {
	val text = "Hello, World!"
	val guild by remember { mutableStateOf(Guild("test")) }
	guild.createChannel("test", ChannelType.GuildTextChannel)
	
	for (i in 0..9) {
		val user = User("Ayfri#000$i")
		guild.addMember(user)
	}
	
	var message: Message
	for (i in 0..5) {
		message = Message("$text $i", guild.getMember("Ayfri#0000")!!.user)
		guild.textChannels.first().createMessage(message)
	}
	
	Guild(guild)
	
	
//	TextChannel(channel)
}

fun main() = application {
	Window(onCloseRequest = ::exitApplication, state = rememberWindowState(WindowPlacement.Maximized)) {
		App()
	}
}
