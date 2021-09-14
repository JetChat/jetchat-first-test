package entities

import Snowflake
import androidx.compose.runtime.mutableStateMapOf
import kotlinx.datetime.Clock

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
}

class DMChannel(val members: Pair<User, User>) : Channel(ChannelType.DMChannel), ITextChannel {
	override val name = members.first.tag
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
	override val id: Snowflake = Clock.System.now().toEpochMilliseconds()
	val position: Int = 0
}

enum class ChannelType {
	DMChannel,
	GuildTextChannel
}

