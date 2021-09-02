
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
	override val messages = mutableMapOf<Snowflake, Message>()
}

class GuildTextChannel(name: String) : GuildChannel(name, ChannelType.GuildTextChannel), ITextChannel {
	override val isInGuild = true
	override val isTextChannel = true
	override val messages = mutableMapOf<Snowflake, Message>()
}

abstract class GuildChannel(val name: String, type: ChannelType) : Channel(type) {
	override val id: Snowflake = Clock.System.now().toEpochMilliseconds()
}

enum class ChannelType {
	DMChannel,
	GuildTextChannel
}
