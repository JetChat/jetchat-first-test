package entities

import Snowflake
import kotlinx.datetime.Clock

class Guild(val name: String) {
	val channels = mutableMapOf<Snowflake, GuildChannel>()
	val textChannels get() = channels.values.filterIsInstance<GuildTextChannel>()
	val createdAt = Clock.System.now()
	val id: Snowflake = createdAt.toEpochMilliseconds()
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
}
