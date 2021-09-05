package entities

import Snowflake
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.datetime.Clock
import loadNetworkImage

class User(var tag: String) {
	val avatarUrl: String? = null
	val avatar: ImageBitmap? get() = if (avatarUrl != null) loadNetworkImage(avatarUrl) else null
	val createdAt = Clock.System.now()
	val id: Snowflake = createdAt.toEpochMilliseconds()
	val username get() = tag.dropLast(5)
	
	companion object {
		val defaultAvatar = Icons.Outlined.Person
	}
}
