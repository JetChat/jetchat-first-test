import kotlinx.datetime.Clock

abstract class Channel(val type: ChannelType) {
	abstract val id: Snowflake
}

class GuildTextChannel(name: String) : GuildChannel(name, ChannelType.GuildTextChannel) {
	val messages = mutableMapOf<Snowflake, Message>()
}

open class GuildChannel(val name: String, type: ChannelType) : Channel(type) {
	override val id: Snowflake = Clock.System.now().toEpochMilliseconds()
}

enum class ChannelType {
	GuildTextChannel
}
