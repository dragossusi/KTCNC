package com.mindovercnc.linuxcnc.screen.programs.picker.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.programs.picker.ProgramPickerComponent
import com.mindovercnc.linuxcnc.widgets.ErrorSnackbar

@Composable
fun ProgramPickerScreenUi(component: ProgramPickerComponent, modifier: Modifier = Modifier) {
    val state by component.state.collectAsState()

    Box(modifier = modifier) {
        ProgramsScreenUi(state, onPathDropped = component::setCurrentFile)
        ErrorSnackbar(
            state.error,
            onDismiss = component::clearError,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}
