import kotlinx.datetime.Clock

class User(val tag: String) {
	var username: String
	val id: Snowflake = Clock.System.now().toEpochMilliseconds()
	
	init {
		username = tag.dropLast(5)
	}
}
