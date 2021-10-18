package entities

import Snowflake
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness1
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.datetime.Clock
import loadNetworkImage

open class Guild(name: String) {
	var name: String = name
		internal set
	val channels = mutableMapOf<Snowflake, GuildChannel>()
	val textChannels get() = channels.values.filterIsInstance<GuildTextChannel>()
	val createdAt = Clock.System.now()
	val iconUrl: String? = null
	val icon: ImageBitmap? get() = if (iconUrl != null) loadNetworkImage(iconUrl) else null
	var id: Snowflake = createdAt.toEpochMilliseconds()
		internal set
	val members = mutableMapOf<Snowflake, GuildMember>()
	
	fun addMember(member: GuildMember): GuildMember {
		members[member.id] = member
		return member
	}
	
	fun addMember(member: User): GuildMember {
		val guildMember = GuildMember(this, member)
		members[member.id] = guildMember
		return guildMember
	}
	
	fun copyFrom(other: Guild) {
		channels.putAll(other.channels)
		members.putAll(other.members)
		id = other.id
		name = other.name
	}
	
	fun getMember(tag: String): GuildMember? {
		return members.values.find { it.tag == tag }
	}
	
	fun removeMember(id: Snowflake): GuildMember? {
		return members.remove(id)
	}
	
	fun createChannel(name: String, type: ChannelType): GuildChannel {
		val channel = when (type) {
			ChannelType.GuildTextChannel -> GuildTextChannel(name, this)
			else -> object : GuildChannel(name, type, this) {
				override val isTextChannel = type.isTextType
			}
		}
		
		channels[channel.id] = channel
		return channel
	}
	
	fun deleteChannel(name: Snowflake): GuildChannel? {
		return channels.remove(name)
	}
	
	companion object {
		val defaultIcon get() = Icons.Default.Brightness1
	}
}

@Composable
fun Guild.asImage(modifier: Modifier) {
	if (iconUrl != null) Image(icon!!, "$name icon", modifier)
	else Image(Guild.defaultIcon, "$name icon", modifier)
}
