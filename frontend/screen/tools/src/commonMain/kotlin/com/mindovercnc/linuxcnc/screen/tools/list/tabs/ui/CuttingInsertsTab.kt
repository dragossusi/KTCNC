package com.mindovercnc.linuxcnc.screen.tools.list.tabs.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.cuttinginsert.CuttingInsertState
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.data.CuttingInsertsColumns
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import androidx.compose.material3.VerticalDivider
import scroll.VerticalScrollbar
import scroll.draggableScroll

private val itemModifier = Modifier.fillMaxWidth()

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CuttingInsertsContent(
    state: CuttingInsertState,
    onEdit: (CuttingInsert) -> Unit,
    onDelete: (CuttingInsert) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {
        val scrollState = rememberLazyListState()

        LazyColumn(modifier = Modifier.draggableScroll(scrollState, scope), state = scrollState) {
            stickyHeader { CuttingInsertHeader(modifier = Modifier.height(40.dp)) }
            itemsIndexed(state.cuttingInserts) { index, item ->
                Surface(
                    // color = gridRowColorFor(index)
                    ) {
                    CuttingInsertView(
                        index = index,
                        item = item,
                        onEditClicked = onEdit,
                        onDeleteClicked = onDelete,
                        modifier = itemModifier
                    )
                }
                Divider(color = Color.LightGray, thickness = 0.5.dp)
            }
        }

        VerticalScrollbar(Modifier.align(Alignment.CenterEnd).width(30.dp), scrollState)
    }
}

@Composable
private fun CuttingInsertHeader(modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            CuttingInsertsColumns.entries.forEach {
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
                if (it != CuttingInsertsColumns.entries.last()) {
                    VerticalDivider(color = Color.LightGray)
                }
            }
        }
    }
}

@Composable
private fun CuttingInsertView(
    index: Int,
    item: CuttingInsert,
    onEditClicked: (CuttingInsert) -> Unit,
    onDeleteClicked: (CuttingInsert) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.height(60.dp)
    ) {
        Text(
            modifier = Modifier.width(CuttingInsertsColumns.Id.size),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            text = (index + 1).toString()
        )
        VerticalDivider()
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            text = item.madeOf.toString()
        )
        VerticalDivider()
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            text = item.code ?: "--"
        )
        VerticalDivider()
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = item.tipRadius.toFixedDigitsString(1),
            style = MaterialTheme.typography.bodyMedium,
        )
        VerticalDivider()
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = "${item.tipAngle.toFixedDigitsString(0)}°",
            style = MaterialTheme.typography.bodyMedium,
        )
        VerticalDivider()
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = item.size.toFixedDigitsString(1),
            style = MaterialTheme.typography.bodyMedium,
        )
        VerticalDivider()
        Text(
            modifier = Modifier.width(CuttingInsertsColumns.Materials.size),
            textAlign = TextAlign.Center,
            text = "Materials here",
            style = MaterialTheme.typography.bodyMedium,
        )
        VerticalDivider()
        Row(
            modifier = Modifier.width(CuttingInsertsColumns.Actions.size),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
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
