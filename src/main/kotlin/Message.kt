import androidx.compose.foundation.BoxWithTooltip
import androidx.compose.foundation.Image
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
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
fun ChannelMessage(message: Message) {
	var showButtons by remember { mutableStateOf(false) }
	Box(
		modifier = Modifier.pointerMoveFilter(
			onEnter = {
				showButtons = true
				true
			}, onExit = {
				showButtons = false
				true
			}
		).fillMaxWidth().background(if (showButtons) Color(245, 245, 245) else Color.White)
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Message(message)
			
			if (showButtons) {
				MessageButtons(message)
			}
		}
	}
}

@Composable
fun Message(message: Message) {
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

@OptIn(ExperimentalUnitApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun MessageButtons(message: Message) {
	BoxWithTooltip(
		tooltipPlacement = TooltipPlacement.CursorPoint(),
		tooltip = {
			Box(
				modifier = Modifier
					.background(MaterialTheme.colors.primaryVariant, shape = CircleShape)
					.padding(5.dp)
			) {
				Text(message.id.toString(), color = Color.White)
			}
		}
	) {
		Box(
			modifier = Modifier
				.offset(y = (message.content.split('\n').size.dec() * 5 + 2).dp)
				.width(40.dp)
				.height(35.dp)
				.clickable(onClick = {
					message.id.toString().copyToClipboard()
				}),
		) {
			Text(
				"ID",
				color = Color.White,
				fontSize = TextUnit(0.8f, TextUnitType.Em),
				modifier = Modifier.align(Alignment.Center).background(MaterialTheme.colors.primary, CircleShape).padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
			)
		}
	}
}
