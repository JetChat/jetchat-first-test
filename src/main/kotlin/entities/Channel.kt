package entities

import Snowflake
import androidx.compose.runtime.mutableStateMapOf
import kotlinx.datetime.Clock

var lastChannelCreatedInstant = Clock.System.now()

abstract class Channel(val type: ChannelType) {
	abstract val id: Snowflake
	abstract val isTextChannel: Boolean
	val createdAt = Clock.System.now()
	
	val asGuildChannel get() = this as GuildChannel
	val asGuildChannelOrNull get() = this as? GuildChannel
	val asGuildTextChannel get() = this as GuildTextChannel
	val asGuildTextChannelOrNull get() = this as? GuildTextChannel
	val asTextChannel get() = this as ITextChannel
	val asTextChannelOrNull get() = this as? ITextChannel
}

interface ITextChannel {
	val name: String
	val id: Snowflake
	val type: ChannelType
	val messages: MutableMap<Snowflake, Message>
	val isInGuild: Boolean
	val isTextChannel get() = true
	
	fun createMessage(message: Message) = messages.put(message.id, message)
	
	fun deleteMessage(messageId: Snowflake) = messages.remove(messageId)
}

class DMChannel(val members: List<User> = emptyList()) : Channel(ChannelType.DMChannel), ITextChannel {
	override val name = members.joinToString(" ") { it.tag }
	override val id: Snowflake = createdAt.toEpochMilliseconds()
	override val isInGuild = false
	override val isTextChannel = true
	override val messages = mutableStateMapOf<Snowflake, Message>()
}

class GuildTextChannel(name: String, guild: Guild) : GuildChannel(name, ChannelType.GuildTextChannel, guild), ITextChannel {
	override val isInGuild = true
	override val isTextChannel = true
	override val messages = mutableStateMapOf<Snowflake, Message>()
}

abstract class GuildChannel(val name: String, type: ChannelType, val guild: Guild) : Channel(type) {
	final override val id: Snowflake
	val position: Int = 0
	val members get() = guild.members
	
	init {
		if (lastChannelCreatedInstant == createdAt) {
			globalId++
		} else {
			lastChannelCreatedInstant = createdAt
			globalId = 0
		}
		
		id = "${createdAt.toEpochMilliseconds()}${globalId.toString().padStart(5, '0')}".toLong()
	}
}

enum class ChannelType {
	DMChannel,
	GuildTextChannel;
	
	val isTextType get() = this == GuildTextChannel
}

