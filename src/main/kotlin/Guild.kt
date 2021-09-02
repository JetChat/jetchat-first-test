import kotlinx.datetime.Clock

class Guild(val name: String) {
	val id = Clock.System.now().toEpochMilliseconds()
	val channels = mutableMapOf<Snowflake, GuildChannel>()
	val members = mutableMapOf<Snowflake, GuildMember>()
}
