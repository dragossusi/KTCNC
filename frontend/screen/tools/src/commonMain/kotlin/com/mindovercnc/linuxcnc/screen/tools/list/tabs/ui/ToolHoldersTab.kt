package com.mindovercnc.linuxcnc.screen.tools.list.tabs.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import com.mindovercnc.linuxcnc.listitem.LabelWithValue
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.data.ToolHolderColumn
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.toolholder.HoldersToolsState
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import androidx.compose.material3.VerticalDivider
import scroll.VerticalScrollbar
import scroll.draggableScroll

private val itemModifier = Modifier.fillMaxWidth()

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToolHoldersContent(
    state: HoldersToolsState,
    onMount: (ToolHolder) -> Unit,
    onDelete: (ToolHolder) -> Unit,
    onLoad: (ToolHolder) -> Unit,
    onEdit: (ToolHolder) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {
        val scrollState = rememberLazyListState()

        LazyColumn(modifier = Modifier.draggableScroll(scrollState, scope), state = scrollState) {
            stickyHeader { ToolHolderHeader(modifier = Modifier.height(40.dp)) }
            items(state.toolHolders) { item ->
                ToolHolderView(
                    item = item,
                    isCurrent = item.holderNumber == state.currentTool,
                    onEditClicked = onEdit,
                    onDeleteClicked = onDelete,
                    onLoadClicked = onLoad,
                    onMountClicked = onMount,
                    modifier = itemModifier,
                    // color = gridRowColorFor(index)
                )
                Divider(color = Color.LightGray, thickness = 0.5.dp)
            }
        }

        VerticalScrollbar(Modifier.align(Alignment.CenterEnd).width(30.dp), scrollState)
    }
}

@Composable
fun ToolHolderHeader(modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            ToolHolderColumn.entries.forEach {
                val textModifier =
                    when (it.size) {
                        Dp.Unspecified -> Modifier.weight(1f)
                        else -> Modifier.width(it.size)
                    }
                Text(
                    modifier = textModifier,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall,
                    text = it.text
                )
                if (it != ToolHolderColumn.entries.last()) {
                    VerticalDivider(color = Color.LightGray)
                }
            }
        }
    }
}

@Composable
private fun ToolHolderView(
    item: ToolHolder,
    isCurrent: Boolean,
    onMountClicked: (ToolHolder) -> Unit,
    onEditClicked: (ToolHolder) -> Unit,
    onDeleteClicked: (ToolHolder) -> Unit,
    onLoadClicked: (ToolHolder) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified
) {
    val nonSelectedModifier = Modifier.height(80.dp)
    val selectedModifier = nonSelectedModifier.border(BorderStroke(1.dp, Color.Blue))

    Surface(modifier = modifier, color = color) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = if (isCurrent) selectedModifier else nonSelectedModifier
        ) {
            Text(
                modifier = Modifier.width(ToolHolderColumn.Id.size),
                text = item.holderNumber.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge
            )
            VerticalDivider()
            Text(
                modifier = Modifier.width(ToolHolderColumn.HolderType.size),
                textAlign = TextAlign.Center,
                text = item.type.name
            )
            VerticalDivider()
            Column(
                modifier = Modifier.width(ToolHolderColumn.Offsets.size),
            ) {
                LabelWithValue("X:", item.xOffset?.toFixedDigitsString() ?: "Not set")
                LabelWithValue("Z:", item.zOffset?.toFixedDigitsString() ?: "Not set")
            }
            VerticalDivider()
            Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                item.latheTool?.let { LatheToolView(latheTool = it) }
                    ?: run {
                        Column(
                            modifier = Modifier.fillMaxWidth(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No tool mounted",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Text(
                                text = "Tap edit to mount a tool",
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
            }
            VerticalDivider()
            Row(
                modifier = Modifier.width(ToolHolderColumn.Actions.size),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(modifier = Modifier, onClick = { onEditClicked.invoke(item) }) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                }
                VerticalDivider()
                IconButton(
                    modifier = Modifier,
                    enabled = isCurrent.not(),
                    onClick = { onDeleteClicked.invoke(item) }
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                }
                VerticalDivider()
                IconButton(
                    modifier = Modifier,
                    enabled = isCurrent.not(),
                    onClick = { onLoadClicked.invoke(item) }
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null)
                }
            }
        }
    }
}
