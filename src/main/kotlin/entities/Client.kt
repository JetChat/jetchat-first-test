package entities

import Snowflake

class ClientGuild(name: String, val position: Int) : Guild(name) {
	constructor(guild: Guild, position: Int) : this(guild.name, position) {
		copyFrom(guild)
	}
}
data class Client(val user: User) {
	val guilds = mutableMapOf<Snowflake, ClientGuild>()
	
	fun addGuild(guild: Guild, position: Int = guilds.size) {
		guilds[guild.id] = ClientGuild(guild, position)
	}
}
