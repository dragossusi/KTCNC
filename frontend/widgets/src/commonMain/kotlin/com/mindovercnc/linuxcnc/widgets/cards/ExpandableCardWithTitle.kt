package com.mindovercnc.linuxcnc.widgets.cards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
fun ExpandableCardWithTitle(
    cardTitle: String,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    contentSpacing: Dp = 8.dp,
    borderColor: Color = Color.DarkGray,
    cardCornerRadius: Dp = 8.dp,
    cardElevation: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(cardCornerRadius),
        modifier = modifier,
        border = BorderStroke(1.dp, SolidColor(borderColor)),
        color = color,
        shadowElevation = cardElevation
    ) {
        ExpandableCardWithTitleContent(
            cardTitle = cardTitle,
            expanded = expanded,
            onExpandChange = onExpandChange,
            contentSpacing = contentSpacing,
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCardWithTitle(
    cardTitle: String,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    enabled: Boolean = true,
    contentSpacing: Dp = 8.dp,
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
        onClick = onClick,
        color = color,
        enabled = enabled
    ) {
        ExpandableCardWithTitleContent(
            cardTitle = cardTitle,
            expanded = expanded,
            onExpandChange = onExpandChange,
            contentSpacing = contentSpacing,
            content = content
        )
    }
}

@Composable
private fun ExpandableCardWithTitleContent(
    cardTitle: String,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    contentSpacing: Dp = 8.dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ExpandableTitle(
            title = cardTitle,
            expanded = expanded,
            onExpandChange = onExpandChange,
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(expanded) {
            HorizontalDivider(thickness = 1.dp, color = LocalContentColor.current)
            Column(verticalArrangement = Arrangement.spacedBy(contentSpacing)) { content.invoke() }
        }
    }
}

@Composable
private fun ExpandableTitle(
    title: String,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.titleMedium,
            text = title
        )
        ExpandIcon(
            expanded = expanded,
            onExpandChange = onExpandChange,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}
