package entities

import Snowflake
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.datetime.Clock
import loadNetworkImage

var lastUserCreatedInstant = Clock.System.now()

class User(var tag: String) {
	val avatarUrl: String? = null
	val avatar: ImageBitmap? get() = if (avatarUrl != null) loadNetworkImage(avatarUrl) else null
	val createdAt = Clock.System.now()
	val id: Snowflake
	val username get() = tag.dropLast(5)
	
	companion object {
		val defaultAvatar = Icons.Outlined.Person
	}
	
	init {
		if (lastUserCreatedInstant == createdAt) {
			globalId++
		} else {
			lastUserCreatedInstant = createdAt
			globalId = 0
		}
		
		id = "${createdAt.toEpochMilliseconds()}${globalId.toString().padStart(3, '0')}".toLong()
	}
}
