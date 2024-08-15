package com.mindovercnc.linuxcnc.widgets.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CardWithTitle(
    cardTitle: String,
    contentSpacing: Dp = 8.dp,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = Color.DarkGray,
    cardCornerRadius: Dp = 8.dp,
    cardElevation: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(cardCornerRadius),
        modifier = modifier,
        border = BorderStroke(1.dp, SolidColor(borderColor)),
        shadowElevation = cardElevation,
        color = color
    ) {
        CardWithTitleContent(cardTitle = cardTitle, contentSpacing = contentSpacing, content = content)
    }
}

@Composable
fun CardWithTitle(
    cardTitle: String,
    onClick: () -> Unit,
    contentSpacing: Dp = 8.dp,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = Color.DarkGray,
    cardCornerRadius: Dp = 8.dp,
    cardElevation: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(cardCornerRadius),
        modifier = modifier,
        color = color,
        border = BorderStroke(1.dp, SolidColor(borderColor)),
        shadowElevation = cardElevation,
        onClick = onClick
    ) {
        CardWithTitleContent(cardTitle = cardTitle, contentSpacing = contentSpacing, content = content)
    }
}

@Composable
private fun CardWithTitleContent(
    cardTitle: String,
    contentSpacing: Dp = 8.dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp),
            style = MaterialTheme.typography.titleMedium,
            text = cardTitle
        )
        HorizontalDivider(thickness = 1.dp, color = LocalContentColor.current)
        Box(modifier = Modifier.padding(contentSpacing)) { content.invoke() }
    }
}
