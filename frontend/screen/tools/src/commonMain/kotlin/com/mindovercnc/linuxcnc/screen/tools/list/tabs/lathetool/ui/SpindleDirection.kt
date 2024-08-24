package com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.widgets.cards.CardWithTitle
import com.mindovercnc.model.SpindleDirection
import ktcnc.frontend.screen.tools.generated.resources.Res
import ktcnc.frontend.screen.tools.generated.resources.spindle_both
import ktcnc.frontend.screen.tools.generated.resources.spindle_fwd
import ktcnc.frontend.screen.tools.generated.resources.spindle_rev
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpindleDirection(
    selectedDirection: SpindleDirection? = null,
    onDirectionSelected: (SpindleDirection) -> Unit
) {
    val itemModifier = Modifier.size(50.dp)
    val items = remember {
        listOf(SpindleDirection.Reverse, SpindleDirection.Both, SpindleDirection.Forward)
    }

    CardWithTitle(cardTitle = "Spindle Direction", modifier = Modifier.width(200.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            items.forEach { item ->
                DirectionItem(
                    spindleDirection = item,
                    active = selectedDirection == item,
                    modifier = itemModifier.onClick { onDirectionSelected(item) })
            }
        }
    }
}

@Composable
fun DirectionItem(
    spindleDirection: SpindleDirection,
    active: Boolean? = null,
    modifier: Modifier = Modifier
) {
    val resource =
        when (spindleDirection) {
            SpindleDirection.Reverse -> Res.drawable.spindle_rev
            SpindleDirection.Forward -> Res.drawable.spindle_fwd
            SpindleDirection.Both -> Res.drawable.spindle_both
            SpindleDirection.None -> null
        }

    val selectedTint =
        when (active) {
            true -> MaterialTheme.colorScheme.primary
            false -> MaterialTheme.colorScheme.surfaceVariant
            else -> LocalContentColor.current
        }

    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        if (resource != null) {
            Icon(
                modifier = Modifier.size(50.dp),
                painter = painterResource(resource),
                tint = selectedTint,
                contentDescription = null,
            )
        }
        Text(
            text = spindleDirection.shortName.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            color = selectedTint)
    }
}
