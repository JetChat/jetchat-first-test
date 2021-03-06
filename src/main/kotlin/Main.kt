
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
import composables.GuildList
import entities.ChannelType
import entities.Client
import entities.Guild
import entities.Message
import entities.User

@Composable
@Preview
fun App() {
	val first = User("User#0000");
	val client = Client(first)
	val text = "Hello, World!"
	val guild by remember { mutableStateOf(Guild("test")) }
	for (i in 0..15) {
		guild.createChannel("test $i", ChannelType.GuildTextChannel)
	}
	
	for (i in 0..9) {
		val user = User("Ayfri#000$i")
		guild.addMember(user)
	}
	
	var message: Message
	for (channel in guild.textChannels) {
		for (i in 0..10) {
			message = Message("$text $i in #${channel.name}", guild.getMember("Ayfri#0000")!!.user)
			channel.createMessage(message)
		}
	}
	client.addGuild(guild)
	GuildList(client)
	Guild(guild)
}

fun main() = application {
	Window(onCloseRequest = ::exitApplication, state = rememberWindowState(WindowPlacement.Maximized)) {
		App()
	}
}
