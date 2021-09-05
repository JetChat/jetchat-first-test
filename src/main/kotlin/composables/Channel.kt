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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import entities.Channel
import entities.ITextChannel

@Composable
fun MessageList(channel: Channel) {
	if (!channel.isTextChannel) return
	val chan = channel.asTextChannelOrNull ?: return
	val messages = chan.messages.values.toList().sortedBy { it.id }
	
	Box(
		modifier = Modifier.fillMaxWidth()
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
fun TextChannel(channel: ITextChannel) {
	Column {
		Row(
			modifier = Modifier.background(Color.LightGray).fillMaxWidth().height(30.dp).padding(horizontal = 10.dp),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically,
		) {
			Text("# ${channel.name}", modifier = Modifier.requiredHeight(20.dp))
			TextChannelButtons(channel)
		}
		MessageList(channel as Channel)
	}
}

@Composable
fun TextChannelButtons(channel: ITextChannel) {
	Box {
	
	}
}
