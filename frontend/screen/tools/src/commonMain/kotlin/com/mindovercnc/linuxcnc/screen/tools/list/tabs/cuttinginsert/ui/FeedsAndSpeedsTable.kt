package com.mindovercnc.linuxcnc.screen.tools.list.tabs.cuttinginsert.ui

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
import androidx.compose.material3.VerticalDivider
import com.mindovercnc.model.FeedsAndSpeeds
import scroll.VerticalScrollbar
import scroll.draggableScroll

private enum class FeedsAndSpeedsColumns(
    val text: String,
    val unit: String? = null,
    val size: Dp = Dp.Unspecified
) {
    Material(text = "Material", size = 120.dp),
    Category(text = "Code", size = 40.dp),
    DOC(text = "DoC (ap)", unit = "mm"),
    Feed(text = "Feed (fn)", unit = "mm/rev"),
    Speed(text = "Speed (vc)", unit = "m/min"),
    Actions(text = "Actions", size = 140.dp),
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedsAndSpeedsTable(
    feedsAndSpeedsList: List<FeedsAndSpeeds>,
    onEdit: (FeedsAndSpeeds) -> Unit,
    onDelete: (FeedsAndSpeeds) -> Unit,
) {
    Box {
        val scope = rememberCoroutineScope()
        val scrollState = rememberLazyListState()

        LazyColumn(modifier = Modifier.draggableScroll(scrollState, scope), state = scrollState) {
            stickyHeader { FeedsAndSpeedsHeader(modifier = Modifier.height(40.dp)) }
            itemsIndexed(feedsAndSpeedsList) { index, item ->
                FeedsAndSpeedsItemView(
                    item = item,
                    onEditClicked = onEdit,
                    onDeleteClicked = onDelete
                )
                Divider(color = Color.LightGray, thickness = 0.5.dp)
            }
        }

        VerticalScrollbar(Modifier.align(Alignment.CenterEnd).width(30.dp), scrollState)
    }
}

@Composable
private fun FeedsAndSpeedsHeader(modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            FeedsAndSpeedsColumns.entries.forEach {
                val columnModifier =
                    when (it.size) {
                        Dp.Unspecified -> Modifier.weight(1f)
                        else -> Modifier.width(it.size)
                    }
                Column(
                    modifier = columnModifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleSmall,
                        text = it.text
                    )
                    if (it.unit != null) {
                        Text(
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall,
                            text = it.unit
                        )
                    }
                }
                if (it != FeedsAndSpeedsColumns.entries.last()) {
                    VerticalDivider(color = Color.LightGray)
                }
            }
        }
    }
}

@Composable
private fun FeedsAndSpeedsItemView(
    item: FeedsAndSpeeds,
    onEditClicked: (FeedsAndSpeeds) -> Unit,
    onDeleteClicked: (FeedsAndSpeeds) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.height(60.dp)
    ) {
        Text(
            modifier = Modifier.width(FeedsAndSpeedsColumns.Material.size),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            text = item.materialName
        )
        VerticalDivider()
        Text(
            modifier = Modifier.width(FeedsAndSpeedsColumns.Category.size),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            text = item.materialCategory.name
        )
        VerticalDivider()
        val formattedAp =
            "${item.ap.start.toFixedDigitsString(2)} - ${
            item.ap.endInclusive.toFixedDigitsString(2)
        }"
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            text = formattedAp
        )
        VerticalDivider()
        val formattedFn =
            "${item.fn.start.toFixedDigitsString(2)} - ${
            item.fn.endInclusive.toFixedDigitsString(2)
        }"
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = formattedFn,
            style = MaterialTheme.typography.bodyMedium,
        )
        VerticalDivider()
        val formattedVc = "${item.vc.start} - ${item.vc.endInclusive}"
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = formattedVc,
            style = MaterialTheme.typography.bodyMedium,
        )
        VerticalDivider()
        Row(
            modifier = Modifier.width(FeedsAndSpeedsColumns.Actions.size),
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
