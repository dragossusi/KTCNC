package com.mindovercnc.linuxcnc.screen.programs.picker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.screen.programs.picker.ProgramPickerState
import com.mindovercnc.linuxcnc.widgets.VerticalDivider
import components.breadcrumb.BreadcrumbView
import components.filesystem.FileSystemView
import editor.EditorEmptyView
import editor.EditorView
import okio.Path

@Composable
fun ProgramsScreenUi(
    state: ProgramPickerState,
    onPathDropped: (Path) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        BreadcrumbView(
            data = state.breadCrumbData,
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        )
        Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
        Row {
            val itemModifier = Modifier.fillMaxSize()
                .weight(1f)
                .widthIn(min = 120.dp)

            // file explorer
            FileSystemView(data = state.fileSystemData, modifier = itemModifier)

            // divider
            VerticalDivider()

            // editor
            val editorModifier = itemModifier.programFileDropTarget { path ->
                onPathDropped(path)
            }
            if (state.editorState != null) {
                EditorView(state.editorState, modifier = editorModifier)
            } else {
                EditorEmptyView(modifier = editorModifier)
            }
        }
    }
}

internal expect fun Modifier.programFileDropTarget(onFileDropped: (Path) -> Unit): Modifier

