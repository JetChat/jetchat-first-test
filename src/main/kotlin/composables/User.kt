package composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import entities.User

@Composable
fun User(user: User) {
	Box {
	
	}
}

@Composable
fun User.asImage(modifier: Modifier) {
	if (avatarUrl != null) Image(avatar!!, "$tag's avatar", modifier)
	else Image(User.defaultAvatar, "$tag's avatar", modifier)
}
