package composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import entities.User

@OptIn(ExperimentalUnitApi::class)
@Composable
fun User(user: User, fontSize: Float = 1f) {
	var isSelected by remember { mutableStateOf(false) }
	
	Box(
		modifier = Modifier.pointerMoveFilter(
			onEnter = {
				isSelected = true
				true
			},
			onExit = {
				isSelected = false
				true
			}
		).background(if (isSelected) Color(245, 245, 245) else Color.White).fillMaxWidth()
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
		) {
			val imageSize = fontSize * 32
			user.asImage(Modifier.height(imageSize.dp).width(imageSize.dp))
			Text(user.username, fontSize = TextUnit(fontSize, TextUnitType.Em))
		}
	}
}

@Composable
fun User.asImage(modifier: Modifier) {
	if (avatarUrl != null) Image(avatar!!, "$tag's avatar", modifier)
	else Image(User.defaultAvatar, "$tag's avatar", modifier)
}
