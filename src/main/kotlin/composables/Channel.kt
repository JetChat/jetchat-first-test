package composables

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import entities.DMChannel
import entities.ITextChannel
import entities.User

@OptIn(ExperimentalUnitApi::class)
@Composable
fun TextChannel(channel: ITextChannel) {
	Column {
		Row(
			modifier = Modifier.background(Color.LightGray).fillMaxWidth().height(40.dp).padding(horizontal = 10.dp),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically,
		) {
			Text((if (channel.isInGuild) "# " else "") + channel.name, fontSize = TextUnit(1.5f, TextUnitType.Em))
			TextChannelButtons(channel)
		}
		Row(
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			MessageList(channel)
			ChannelMembers(channel)
		}
	}
}

@Composable
fun MessageList(channel: ITextChannel) {
	val messages = channel.messages.values.toList().sortedBy { it.id }
	
	Box(
		modifier = Modifier.fillMaxWidth(0.9f)
	) {
		val listState = rememberLazyListState()
		
		LazyColumn(
			verticalArrangement = Arrangement.spacedBy(8.dp),
			state = listState,
		) {
			items(messages, { it.id }) {
				ChannelMessage(it)
			}
		}
		
		VerticalScrollbar(
			modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
			adapter = rememberScrollbarAdapter(scrollState = listState)
		)
	}
}

@Composable
fun ChannelMembers(channel: ITextChannel) {
	Box(
		modifier = Modifier.fillMaxWidth()
	) {
		if (channel.isInGuild) {
		
		} else {
			ChannelUserList((channel as DMChannel).members.toList())
		}
	}
}

@Composable
fun ChannelUserList(users: List<User>) {
	Box {
		val listState = rememberLazyListState()
		LazyColumn(
			verticalArrangement = Arrangement.spacedBy(5.dp),
			state = listState
		) {
			items(users.sortedBy { it.tag }) {
				User(it, 1.2f)
			}
		}
		
		VerticalScrollbar(
			modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
			adapter = rememberScrollbarAdapter(scrollState = listState)
		)
	}
	
}

@Composable
fun TextChannelButtons(channel: ITextChannel) {
	Box {
	
	}
}
