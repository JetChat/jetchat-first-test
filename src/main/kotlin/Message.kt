
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock

var lastMessageCreatedInstant = Clock.System.now()
var globalId = 0

data class Message(val content: String, val author: User) {
	val createdAt = Clock.System.now()
	val id: Snowflake
	
	init {
		if (lastMessageCreatedInstant == createdAt) {
			globalId++
		} else {
			lastMessageCreatedInstant = createdAt
			globalId = 0
		}
		
		id = "${createdAt.toEpochMilliseconds()}${globalId.toString().padStart(5, '0')}".toLong()
	}
}

@Composable
fun Message(message: Message) {
	Box {
		Row {
			val imageModifier = Modifier.height(32.dp).width(32.dp).align(Alignment.CenterVertically)
			if (message.author.avatarUrl != null) Image(message.author.avatar!!, "${message.author.tag}'s avatar", imageModifier)
			else Image(User.defaultAvatar, "${message.author.tag}'s avatar", imageModifier)
			Column {
				Text(message.author.username)
				Text(message.content)
			}
		}
	}
}
