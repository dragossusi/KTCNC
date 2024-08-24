package com.mindovercnc.linuxcnc.screen.programs.preview.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.programs.preview.ProgramPreviewComponent
import editor.EditorEmptyView
import editor.EditorView

@Composable
fun ProgramPreviewUi(component: ProgramPreviewComponent?, modifier: Modifier = Modifier) {
    if (component == null) {
        EditorEmptyView(modifier = modifier)
        return
    }

    val state = component.state.collectAsState().value

    EditorView(state.editorState, modifier = modifier)
}
