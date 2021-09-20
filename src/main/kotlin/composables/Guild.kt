package composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp
import entities.ChannelType.GuildTextChannel
import entities.Guild
import entities.GuildChannel

@Composable
fun ChannelList(guild: Guild, modifier: Modifier = Modifier) {
	val channels = guild.channels.values.toList().sortedBy { it.position }
	
	Box(
		modifier = Modifier.requiredWidthIn(100.dp, 200.dp).then(modifier)
	) {
		LazyColumn {
			items(channels) {
				GuildChannel(it)
			}
		}
	}
}

@Composable
fun Guild(guild: Guild) {
	val selectedChannelPosition = remember { mutableStateOf(0) }
	val selectedChannel = guild.channels.values.find { it.position == selectedChannelPosition.value } ?: guild.channels.values.minByOrNull { it.position }!!
	
	Row {
		ChannelList(guild)
		if (selectedChannel.isTextChannel) {
			TextChannel(selectedChannel.asTextChannel)
		}
	}
}

@Composable
fun GuildChannel(guildChannel: GuildChannel, modifier: Modifier = Modifier) {
	var isSelected by remember { mutableStateOf(false) }
	
	Box(
		modifier = Modifier.pointerMoveFilter(
			onEnter = {
				isSelected = true
				true
			},
			onExit = {
				isSelected = false
				true
			}
		).background(if (isSelected) Color(245, 245, 245) else Color.White).fillMaxWidth()
	) {
		Row(
			modifier = Modifier.padding(horizontal = 5.dp).then(modifier)
		) {
			Icon(
				when (guildChannel.type) {
					GuildTextChannel -> Icons.Outlined.Tag
					else -> Icons.Outlined.Tag
				}, "channelIcon"
			)
			Text(guildChannel.name, Modifier.align(Alignment.CenterVertically))
		}
	}
}

@Composable
fun GuildMemberList(guild: Guild) {
	val members = guild.members.values.sortedBy { it.tag }
	LazyColumn {
		items(members) {
			User(it.user)
		}
	}
}
