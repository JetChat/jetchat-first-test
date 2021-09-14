package composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import entities.ChannelType.GuildTextChannel
import entities.Guild
import entities.GuildChannel

@Composable
fun ChannelList(guild: Guild, modifier: Modifier = Modifier) {
	val channels = guild.channels.values.toList().sortedBy { it.position }
	
	Box(
		modifier = Modifier.then(modifier)
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
		if(selectedChannel.isTextChannel) {
			TextChannel(selectedChannel.asTextChannel)
		}
	}
}

@Composable
fun GuildChannel(guildChannel: GuildChannel) {
	Row {
		Text(guildChannel.name)
		Icon(
			when (guildChannel.type) {
				GuildTextChannel -> Icons.Default.Tag
				else -> Icons.Default.Tag
			}, "channelIcon"
		)
	}
}
