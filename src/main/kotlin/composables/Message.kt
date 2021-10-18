package composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import copyToClipboard
import entities.Message


@Composable
fun ChannelMessage(message: Message) {
	val showButtons = remember { mutableStateOf(false) }
	
	Hoverable(
		hovered = showButtons,
	) {
		Row(
			modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Message(message)
			
			if (showButtons.value) {
				MessageButtons(message)
			}
		}
	}
}

@Composable
fun Message(message: Message) {
	Row {
		message.author.asImage(Modifier.height(32.dp).width(32.dp).align(Alignment.CenterVertically))
		Column {
			Text(message.author.username, fontWeight = FontWeight.SemiBold)
			Text(message.content)
		}
	}
}

@OptIn(ExperimentalUnitApi::class, ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun MessageButtons(message: Message) {
	TooltipArea(
		tooltip = {
			Tooltip {
				Text(message.id.toString(), color = Color.White)
			}
		},
		tooltipPlacement = TooltipPlacement.CursorPoint()
	) {
		Box(
			modifier = Modifier
				.offset(y = (message.content.split('\n').size.dec() * 5 + 2).dp)
				.width(40.dp)
				.height(35.dp)
				.clickable(onClick = {
					message.id.toString().copyToClipboard()
				}),
		) {
			Text(
				"ID",
				color = Color.White,
				fontSize = TextUnit(0.8f, TextUnitType.Em),
				modifier = Modifier
					.align(Alignment.Center)
					.background(MaterialTheme.colors.primary, RoundedCornerShape(30))
					.padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
			)
		}
	}
}
