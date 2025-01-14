package com.mindovercnc.linuxcnc.screen.status.root.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.domain.model.Message
import com.mindovercnc.linuxcnc.screen.status.root.StatusRootComponent
import com.mindovercnc.linuxcnc.screen.status.root.StatusRootState
import ktcnc.frontend.screen.status.generated.resources.Res
import ktcnc.frontend.screen.status.generated.resources.status_grid_column_message
import ktcnc.frontend.screen.status.generated.resources.status_grid_column_type
import org.jetbrains.compose.resources.stringResource
import scroll.draggableScroll

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StatusRootScreenUi(component: StatusRootComponent, modifier: Modifier = Modifier) {
    val state by component.state.collectAsState()

    Table(state, modifier.padding(8.dp))
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun Table(
    state: StatusRootState,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Box(modifier) {
        Surface(shape = RoundedCornerShape(8.dp), border = BorderStroke(1.dp, Color.LightGray)) {
            LazyColumn(modifier = Modifier.fillMaxWidth().draggableScroll(scrollState, scope), state = scrollState) {
                stickyHeader { MessagesHeader() }
                items(state.messages) { item -> MessageRow(item) }
            }
        }
    }
}

@Composable
private fun MessagesHeader(modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.primaryContainer, modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.width(100.dp),
                textAlign = TextAlign.Center,
                text = stringResource(Res.string.status_grid_column_type),
            )
            VerticalDivider(color = Color.LightGray)
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(Res.string.status_grid_column_message),
            )
        }
    }
}

@Composable
private fun MessageRow(item: Message, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {},
    ) {
        Text(
            modifier = Modifier.width(100.dp).padding(8.dp),
            textAlign = TextAlign.Center,
            text = item.level.name,
        )
        VerticalDivider(thickness = 2.dp, color = Color.LightGray)
        Text(
            item.text,
            modifier = Modifier.weight(1f).padding(8.dp),
        )
    }
}
