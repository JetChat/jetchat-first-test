package entities

import Snowflake
import kotlinx.datetime.Clock

class Guild(val name: String) {
	val channels = mutableMapOf<Snowflake, GuildChannel>()
	val createdAt = Clock.System.now()
	val id: Snowflake = createdAt.toEpochMilliseconds()
	val members = mutableMapOf<Snowflake, GuildMember>()
	
	fun createChannel(name: String, type: ChannelType): GuildChannel? {
		val channel = object : GuildChannel(name, type, this) {
			override val isTextChannel = type.isTextType
		}
		return channels.put(channel.id, channel)
	}
	
	fun deleteChannel(name: Snowflake): GuildChannel? {
		return channels.remove(name)
	}
}
