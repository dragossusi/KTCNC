package com.mindovercnc.linuxcnc.screen.programs.picker.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.programs.picker.ProgramPickerComponent
import com.mindovercnc.linuxcnc.screen.programs.root.ProgramsRootComponent

@Composable
fun ProgramPickerFab(
    rootComponent: ProgramsRootComponent,
    component: ProgramPickerComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.collectAsState()

    state.editorState?.let { editorState ->
        ExtendedFloatingActionButton(
            text = { Text("Load Program") },
            onClick = { rootComponent.openProgram(editorState.editor.file) },
            icon = {
                Icon(
                    Icons.Default.ExitToApp,
                    contentDescription = null,
                )
            },
            modifier = modifier
        )
    }
}
