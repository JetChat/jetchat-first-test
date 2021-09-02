
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.datetime.Clock

class User(var tag: String) {
	val username get() = tag.dropLast(5)
	val avatarUrl: String? = null
	val avatar: ImageBitmap? get() = if(avatarUrl != null) loadNetworkImage(avatarUrl) else null
	val id: Snowflake = Clock.System.now().toEpochMilliseconds()
	
	companion object {
		val defaultAvatar = Icons.Outlined.Person
	}
}
