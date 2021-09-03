import kotlinx.datetime.Clock

class Guild(val name: String) {
	val channels = mutableMapOf<Snowflake, GuildChannel>()
	val createdAt = Clock.System.now()
	val id: Snowflake = createdAt.toEpochMilliseconds()
	val members = mutableMapOf<Snowflake, GuildMember>()
}
