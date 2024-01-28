package com.mindovercnc.linuxcnc.screen.programs.programloaded.ui

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ProgramLoadedComponent
import com.mindovercnc.linuxcnc.widgets.ZoomControls

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProgramCanvas(
    component: ProgramLoadedComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.collectAsState()
    Box(modifier) {
        VisualTurning(
            state = state.visualTurningState,
            modifier =
            Modifier.fillMaxWidth()
                .height(400.dp)
                .onSizeChanged { component.setViewportSize(it) }
                .onPointerEvent(PointerEventType.Scroll) {
                    val change = it.changes.first()
                    val zoomIn = change.scrollDelta.y < 0
                    if (zoomIn) {
                        component.zoomIn()
                    } else {
                        component.zoomOut()
                    }
                }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        component.translate(dragAmount)
                    }
                }
        )
        ZoomControls(
            value = state.visualTurningState.scale,
            onZoomIn = component::zoomIn,
            onZoomOut = component::zoomOut,
            zoomInEnabled = state.visualTurningState.canZoomIn,
            zoomOutEnabled = state.visualTurningState.canZoomOut,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp)
        )
        CenterButton(
            onClick = component::center,
            modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)
        )
    }
}

@Composable
private fun CenterButton(onClick: () -> Unit, modifier: Modifier) {
    IconButton(onClick = onClick, modifier) {
        Icon(imageVector = Icons.Default.Adjust, contentDescription = null)
    }
}
