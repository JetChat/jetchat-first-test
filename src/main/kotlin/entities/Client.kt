package entities

import Snowflake

class ClientGuild(name: String, val position: Int) : Guild(name)

data class Client(val user: User) {
	val guilds = mutableMapOf<Snowflake, ClientGuild>()
}
