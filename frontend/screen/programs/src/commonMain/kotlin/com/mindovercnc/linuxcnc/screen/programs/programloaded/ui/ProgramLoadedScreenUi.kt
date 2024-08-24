package com.mindovercnc.linuxcnc.screen.programs.programloaded.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ProgramLoadedComponent
import androidx.compose.material3.VerticalDivider
import editor.EditorView
import editor.FileNameHeader

@Composable
internal fun ProgramLoadedScreenUi(
    component: ProgramLoadedComponent,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        StartContent(component, Modifier.weight(1f))

        VerticalDivider(thickness = 1.dp)

        EndContent(component)
    }
}

@Composable
private fun StartContent(component: ProgramLoadedComponent, modifier: Modifier = Modifier) {
    val state by component.state.collectAsState()
    Column(modifier = modifier) {
        ProgramCanvas(component)
        val editorState = state.editorState
        if (editorState != null) {
            EditorView(
                editorState,
                showFileName = false,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun EndContent(component: ProgramLoadedComponent) {
    val state by component.state.collectAsState()

    Column(modifier = Modifier.width(420.dp)) {
        // file name
        state.editorState?.let { editorState ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                FileNameHeader(editorState.editor.file)
            }
        }
        state.toolChangeModel?.let {
            ToolChangeDialog(
                toolChangeModel = it,
                confirmationClick = component::confirmToolChanged,
                abortClick = component::cancelToolChange
            )
        }

        state.positionModel?.let {
            ProgramCoordinatesView(currentWcs = state.currentWcs, positionModel = it)
        }
        StatusView(
            machineStatus = state.machineStatus,
            modifier = Modifier.weight(1f).padding(8.dp)
        )
        ActiveCodesView(
            activeCodes = state.activeCodes,
            modifier = Modifier.fillMaxWidth().height(80.dp),
            onCodeClicked = component::onActiveCodeClicked
        )
    }
}
