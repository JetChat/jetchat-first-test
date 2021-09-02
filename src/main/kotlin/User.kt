import kotlinx.datetime.Clock

class User(var tag: String) {
	val username get() = tag.dropLast(5)
	val avatarUrl: String? = null
	val id: Snowflake = Clock.System.now().toEpochMilliseconds()
}
