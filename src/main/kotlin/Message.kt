import kotlinx.datetime.Clock

data class Message(val content: String, val author: User) {
	val id: Snowflake
	
	init {
		val now = Clock.System.now()
		id = "${now.toEpochMilliseconds()}${now.nanosecondsOfSecond.toString().takeLast(3)}".toLong()
	}
}
