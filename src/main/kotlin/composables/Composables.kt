package composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp

@Composable
fun Hoverable(
	modifier: Modifier = Modifier,
	onHover: () -> Unit = {},
	onExit: () -> Unit = {},
	hovered: MutableState<Boolean>? = null,
	baseColor: Color = Color.White,
	hoverColor: Color = Color(245, 245, 245),
	content: @Composable () -> Unit
) {
	var isSelected by remember { mutableStateOf(false) }
	if (hovered != null) hovered.value = isSelected
	
	Box(
		modifier = Modifier.pointerMoveFilter(
			onEnter = {
				isSelected = true
				onHover()
				true
			},
			onExit = {
				isSelected = false
				onExit()
				true
			}
		).background(if (isSelected) hoverColor else baseColor).fillMaxWidth().then(modifier)
	) {
		content()
	}
}

@Composable
fun Tooltip(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
	Box(
		modifier = Modifier
			.background(MaterialTheme.colors.primaryVariant, shape = RoundedCornerShape(30))
			.padding(5.dp).then(modifier)
	) {
		content()
	}
}
