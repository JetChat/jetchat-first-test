package entities

import Snowflake
import kotlinx.datetime.Clock

var lastMessageCreatedInstant = Clock.System.now()
var globalId = 0

data class Message(val content: String, val author: User) {
	val createdAt = Clock.System.now()
	val id: Snowflake
	
	init {
		if (lastMessageCreatedInstant == createdAt) {
			globalId++
		} else {
			lastMessageCreatedInstant = createdAt
			globalId = 0
		}
		
		id = "${createdAt.toEpochMilliseconds()}${globalId.toString().padStart(5, '0')}".toLong()
	}
}

