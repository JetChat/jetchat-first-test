
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
import entities.Guild
import entities.GuildMember
import entities.GuildTextChannel
import entities.Message
import entities.User

@Composable
@Preview
fun App() {
	val text = "Hello, World!"
	val guild by remember { mutableStateOf(Guild("test")) }
	val channel = GuildTextChannel("test", guild)
	guild.channels[channel.id] = channel
	
	val user = User("Ayfri#0000")
	val user2 = User("Ayfri#0001")
	guild.members[user.id] = GuildMember(guild, user)
	guild.members[user2.id] = GuildMember(guild, user2)
	
	var message: Message
	for (i in 0..5) {
		message = Message("$text $i", user)
		channel.messages[message.id] = message
	}
	
	Guild(guild)
	
	
//	TextChannel(channel)
}

fun main() = application {
	Window(onCloseRequest = ::exitApplication, state = rememberWindowState(WindowPlacement.Maximized)) {
		App()
	}
}
