
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock

abstract class Channel(val type: ChannelType) {
	abstract val id: Snowflake
	abstract val isTextChannel: Boolean
	
	val asGuildChannel get() = this as GuildChannel
	val asGuildChannelOrNull get() = this as? GuildChannel
	val asGuildTextChannel get() = this as GuildTextChannel
	val asGuildTextChannelOrNull get() = this as? GuildTextChannel
	val asTextChannel get() = this as ITextChannel
	val asTextChannelOrNull get() = this as? ITextChannel
}

interface ITextChannel {
	val id: Snowflake
	val type: ChannelType
	val messages: MutableMap<Snowflake, Message>
	val isInGuild: Boolean
	val isTextChannel get() = true
}

class DMChannel(val members: Pair<User, User>) : Channel(ChannelType.DMChannel), ITextChannel {
	override val id: Snowflake = Clock.System.now().toEpochMilliseconds()
	override val isInGuild = false
	override val isTextChannel = true
	override val messages = mutableStateMapOf<Snowflake, Message>()
}

class GuildTextChannel(name: String) : GuildChannel(name, ChannelType.GuildTextChannel), ITextChannel {
	override val isInGuild = true
	override val isTextChannel = true
	override val messages = mutableStateMapOf<Snowflake, Message>()
}

abstract class GuildChannel(val name: String, type: ChannelType) : Channel(type) {
	override val id: Snowflake = Clock.System.now().toEpochMilliseconds()
}

enum class ChannelType {
	DMChannel,
	GuildTextChannel
}

@Composable
fun MessageList(channel: Channel) {
	if (!channel.isTextChannel) return
	val chan = channel.asTextChannelOrNull ?: return
	val messages = chan.messages.values.toList()
	
	Box {
		val listState = rememberLazyListState()
		
		LazyColumn(
			verticalArrangement = Arrangement.spacedBy(4.dp),
			state = listState,
		) {
			items(messages, { it.id }) {
				Message(it)
			}
		}
		
		VerticalScrollbar(
			modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
			adapter = rememberScrollbarAdapter(scrollState = listState)
		)
	}
}
