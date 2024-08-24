package com.mindovercnc.linuxcnc.screen.tools.list.tabs.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import com.mindovercnc.linuxcnc.listitem.LabelWithValue
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.data.LatheToolColumns
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.LatheToolsState
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.ui.DirectionItem
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.ui.TipOrientationUi
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import scroll.VerticalScrollbar
import scroll.draggableScroll

private val itemModifier = Modifier.fillMaxWidth()

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LatheToolsContent(
    state: LatheToolsState,
    modifier: Modifier = Modifier,
    onDelete: (LatheTool) -> Unit,
    onEdit: (LatheTool) -> Unit
) {
    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {
        val scrollState = rememberLazyListState()

        LazyColumn(modifier = Modifier.draggableScroll(scrollState, scope), state = scrollState) {
            stickyHeader { LatheToolHeader(modifier = Modifier.height(40.dp)) }
            itemsIndexed(state.latheTools) { index, item ->
                Surface(
                    // color = gridRowColorFor(index)
                    ) {
                        GenericToolView(
                            index = index,
                            item = item,
                            onEditClicked = onEdit,
                            onDeleteClicked = onDelete,
                            modifier = itemModifier) {
                                when (item) {
                                    is LatheTool.Turning ->
                                        TurningToolView(item = item, modifier = itemModifier)
                                    is LatheTool.Boring -> BoringToolView(item = item)
                                    is LatheTool.Drilling -> DrillingToolView(item = item)
                                    is LatheTool.Parting -> PartingToolView(item = item)
                                    else -> {
                                        Text("Not implemented: $item")
                                    }
                                }
                            }
                    }
                HorizontalDivider()
            }
        }

        VerticalScrollbar(Modifier.align(Alignment.CenterEnd).width(30.dp), scrollState)
    }
}

@Composable
fun LatheToolHeader(modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            LatheToolColumns.entries.forEach {
                val textModifier =
                    when (it.size) {
                        Dp.Unspecified -> Modifier.weight(1f)
                        else -> Modifier.width(it.size)
                    }
                Text(
                    modifier = textModifier,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall,
                    text = it.text)
                if (it != LatheToolColumns.entries.last()) {
                    VerticalDivider(color = Color.LightGray)
                }
            }
        }
    }
}

@Composable
private fun GenericToolView(
    index: Int,
    item: LatheTool,
    onEditClicked: (LatheTool) -> Unit,
    onDeleteClicked: (LatheTool) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.height(60.dp)) {
            Text(
                text = (index + 1).toString(),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(LatheToolColumns.Id.size),
            )
            VerticalDivider()
            Text(
                text = item::class.simpleName!!,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.width(LatheToolColumns.ToolType.size))
            VerticalDivider()
            // todo remove this
            Column(modifier = Modifier.padding(horizontal = 8.dp).weight(1f), content = content)
            VerticalDivider()
            Row(
                modifier = Modifier.width(LatheToolColumns.Orientation.size),
                horizontalArrangement = Arrangement.Center) {
                    TipOrientationUi(
                        orientation = item.tipOrientation,
                        modifier = Modifier.padding(8.dp),
                        onClick = {})
                }
            VerticalDivider()
            Row(
                modifier = Modifier.width(LatheToolColumns.Rotation.size).padding(8.dp),
                horizontalArrangement = Arrangement.Center) {
                    DirectionItem(spindleDirection = item.spindleDirection)
                }
            VerticalDivider()
            UsageTime(
                seconds = item.secondsUsed, modifier = Modifier.width(LatheToolColumns.Usage.size))
            VerticalDivider()
            Row(
                modifier = Modifier.width(LatheToolColumns.Actions.size),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly) {
                    IconButton(modifier = Modifier, onClick = { onEditClicked.invoke(item) }) {
                        Icon(Icons.Default.Edit, contentDescription = null)
                    }
                    VerticalDivider()
                    IconButton(modifier = Modifier, onClick = { onDeleteClicked.invoke(item) }) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                    }
                }
        }
}

@Composable
private fun TurningToolView(
    item: LatheTool.Turning,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.height(60.dp)) {
            CutterProperties(insert = item.insert, modifier = Modifier.weight(1f))
        }
}

@Suppress("UnusedReceiverParameter")
@Composable
private fun ColumnScope.BoringToolView(item: LatheTool.Boring) {
    CutterProperties(insert = item.insert, modifier = itemModifier)
    LabelWithValue(
        label = "Minimum Diameter:",
        value = item.minBoreDiameter.toFixedDigitsString(1),
        paddingStart = 0.dp)
}

@Suppress("UnusedReceiverParameter")
@Composable
private fun ColumnScope.DrillingToolView(item: LatheTool.Drilling) {
    LabelWithValue(
        label = "Tool Diameter:",
        value = item.toolDiameter.toFixedDigitsString(1),
        paddingStart = 0.dp)
    LabelWithValue(
        label = "Maximum Z Depth:",
        value = item.maxZDepth.toFixedDigitsString(1),
        paddingStart = 0.dp)
}

@Suppress("UnusedReceiverParameter")
@Composable
private fun ColumnScope.PartingToolView(
    item: LatheTool.Parting,
) {
    val bladeWidthText = remember(item.bladeWidth) { item.bladeWidth.toFixedDigitsString(1) }
    val maxXDepth = remember(item.maxXDepth) { item.maxXDepth.toFixedDigitsString(1) }

    CutterProperties(insert = item.insert, modifier = itemModifier)
    LabelWithValue(label = "Blade Width:", value = bladeWidthText, paddingStart = 0.dp)
    LabelWithValue(label = "Maximum X Depth:", value = maxXDepth, paddingStart = 0.dp)
}

@Composable
fun CutterProperties(insert: CuttingInsert, modifier: Modifier = Modifier) {
    val radiusText = remember(insert.tipRadius) { "r${insert.tipRadius.toFixedDigitsString(1)}" }

    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Cutter Type:", style = MaterialTheme.typography.labelLarge)
        Text(text = insert.madeOf.name, style = MaterialTheme.typography.bodyMedium)

        Text(
            text = insert.code ?: "--",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold)

        Text(
            text = radiusText,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun UsageTime(seconds: Double, modifier: Modifier = Modifier.wrapContentWidth()) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
            val secondsText =
                remember(seconds) {
                    LocalTime.fromSecondOfDay(seconds.toInt()).format(LocalTime.Formats.ISO)
                }
            Icon(Icons.Default.DateRange, contentDescription = null)
            Text(text = secondsText, style = MaterialTheme.typography.bodyMedium)
        }
}
