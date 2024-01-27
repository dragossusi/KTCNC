package com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.TipOrientation
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private val pickerModifier = Modifier.size(50.dp)

private val arrangement = Arrangement.spacedBy(4.dp)

@Composable
fun ToolOrientationPicker(
    selectedOrientation: TipOrientation?,
    orientationSelected: (TipOrientation) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = remember { TipOrientation.asMatrix() }
    Column(verticalArrangement = arrangement, modifier = modifier) {
        items.forEach { triple ->
            TipOrientationRow(
                selectedOrientation = selectedOrientation,
                items = triple,
                onItemClick = orientationSelected
            )
        }
    }
}

@Composable
private fun TipOrientationRow(
    selectedOrientation: TipOrientation?,
    items: Triple<TipOrientation, TipOrientation, TipOrientation>,
    onItemClick: (TipOrientation) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(horizontalArrangement = arrangement, modifier = modifier) {
        TipOrientationUi(
            orientation = items.first,
            active = selectedOrientation == items.first,
            onClick = onItemClick,
            modifier = pickerModifier
        )
        TipOrientationUi(
            orientation = items.second,
            active = selectedOrientation == items.second,
            onClick = onItemClick,
            modifier = pickerModifier
        )
        TipOrientationUi(
            orientation = items.third,
            active = selectedOrientation == items.third,
            onClick = onItemClick,
            modifier = pickerModifier
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TipOrientationUi(
    orientation: TipOrientation,
    active: Boolean? = null,
    onClick: (TipOrientation) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val fileName = remember(orientation) {
        TipOrientationPainter.from(orientation)
    }

    Surface(
        modifier = modifier,
        onClick = { onClick(orientation) },
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(4.dp),
        enabled = enabled,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        val selectedTint =
            when (active) {
                true -> MaterialTheme.colorScheme.primary
                false -> MaterialTheme.colorScheme.surface
                else -> LocalContentColor.current
            }
        Icon(
            painter = painterResource(fileName),
            tint = selectedTint,
            contentDescription = null,
        )
    }
}
