package com.mindovercnc.linuxcnc.screen.tools.add.toolholder.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.listitem.ValueSetting
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.screen.tools.add.toolholder.AddEditToolHolderComponent
import com.mindovercnc.linuxcnc.screen.tools.add.toolholder.AddEditToolHolderState
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.ui.LatheToolView
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsRootComponent
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolderType
import androidx.compose.material3.VerticalDivider
import scroll.draggableScroll

@Composable
fun AddEditToolHolderScreenUi(
    rootComponent: ToolsRootComponent,
    component: AddEditToolHolderComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.collectAsState()
    if (state.isFinished) {
        LaunchedEffect(Unit) { rootComponent.navigateUp() }
    }
    AddEditToolHolderScreenUi(
        state = state,
        onHolderNumber = component::setHolderNumber,
        onHolderType = component::setHolderType,
        onLatheTool = component::setLatheTool,
        modifier = modifier
    )
}

@Composable
private fun AddEditToolHolderScreenUi(
    state: AddEditToolHolderState,
    onHolderNumber: (Int) -> Unit,
    onHolderType: (ToolHolderType) -> Unit,
    onLatheTool: (LatheTool) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxSize()) {
        StartContent(
            state,
            onHolderNumber,
            onHolderType,
            modifier = Modifier.weight(1f).widthIn(min = 300.dp)
        )

        VerticalDivider()

        EndContent(state, onLatheTool, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun StartContent(
    state: AddEditToolHolderState,
    onHolderNumber: (Int) -> Unit,
    onHolderType: (ToolHolderType) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = remember { ToolHolderType.entries }
    val holdersScrollState = rememberLazyGridState()
    val scope = rememberCoroutineScope()

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (state.holderNumber == null) {
            ValueSetting(
                settingName = "Holder #",
                value = "0",
                inputType = InputType.TOOL_HOLDER_NO,
                onValueChanged = {
                    val doubleValue = it.toDouble()
                    onHolderNumber(doubleValue.toInt())
                },
                modifier = Modifier.fillMaxWidth(),
                inputModifier = Modifier.width(100.dp)
            )
            Divider()
            Spacer(Modifier.height(24.dp))
        }

        Text(
            modifier = Modifier.padding(8.dp),
            text = "Tool Holder Type",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )

        LazyVerticalGrid(
            modifier = Modifier.draggableScroll(holdersScrollState, scope),
            state = holdersScrollState,
            contentPadding = PaddingValues(8.dp),
            columns = GridCells.Adaptive(120.dp),
        ) {
            items(items) { item ->
                ToolHolderView(
                    modifier = Modifier.padding(8.dp),
                    type = item,
                    onClick = onHolderType,
                    isSelected = item == state.type
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun EndContent(
    state: AddEditToolHolderState,
    onLatheTool: (LatheTool) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    val toolsScrollState = rememberLazyListState()
    LazyColumn(
        modifier = modifier.draggableScroll(toolsScrollState, scope),
        state = toolsScrollState
    ) {
        stickyHeader {
            val headerText =
                remember(state.unmountedLatheTools.isEmpty()) {
                    when (state.unmountedLatheTools.isEmpty()) {
                        true -> "No tools of this type"
                        false -> "Tools not mounted yet"
                    }
                }

            Text(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                text = headerText,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
        }

        itemsIndexed(state.unmountedLatheTools) { index, item ->
            Divider(color = Color.LightGray, thickness = 0.5.dp)
            LatheToolView(
                latheTool = item,
                onSelected = onLatheTool,
                isSelected = item == state.latheTool
            )
            if (state.unmountedLatheTools.lastIndex == index) {
                Divider(color = Color.LightGray, thickness = 0.5.dp)
            }
        }
    }
}
