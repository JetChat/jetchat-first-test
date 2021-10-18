package composables

import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import entities.ChannelType.GuildTextChannel
import entities.Client
import entities.Guild
import entities.GuildChannel
import entities.asImage

@Composable
fun ChannelList(guild: Guild, onSelect: (GuildChannel) -> Unit = {}, modifier: Modifier = Modifier) {
	val channels = guild.channels.values.toList().sortedBy { it.position }
	
	Box(
		modifier = Modifier.fillMaxWidth(0.1f).then(modifier)
	) {
		LazyColumn {
			items(channels) {
				GuildChannel(it, modifier = Modifier.clickable {
					onSelect(it)
				})
			}
		}
	}
}

@Composable
fun Guild(guild: Guild) {
	val selectedChannel = remember {
		mutableStateOf(guild.channels.values.minByOrNull { it.position }!!)
	}
	
	Row {
		Column {
			GuildName(guild)
			ChannelList(guild, {
				selectedChannel.value = it
			})
		}
		if (selectedChannel.value.isTextChannel) {
			TextChannel(selectedChannel.value.asTextChannel, Modifier.weight(1f).fillMaxWidth(0.9f))
		}
	}
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun GuildName(guild: Guild) {
	Box(
		modifier = Modifier.requiredHeight(50.dp).fillMaxWidth(0.1f)
	) {
		Text(guild.name, fontSize = TextUnit(1.8f, TextUnitType.Em), modifier = Modifier.align(Alignment.Center))
	}
}

@Composable
fun GuildChannel(guildChannel: GuildChannel, modifier: Modifier = Modifier) {
	Hoverable {
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

@Composable
fun GuildListItem(guild: Guild) {
	val imageSize = 32
	TooltipArea(
		tooltip = {
			Text(guild.name)
		}
	) {
		guild.asImage(Modifier.height(imageSize.dp).width(imageSize.dp))
	}
}

@Composable
fun GuildList(client: Client) {
	val guilds = client.guilds.values.sortedBy { it.position }
	LazyColumn {
		items(guilds) {
			GuildListItem(it)
		}
	}
}
