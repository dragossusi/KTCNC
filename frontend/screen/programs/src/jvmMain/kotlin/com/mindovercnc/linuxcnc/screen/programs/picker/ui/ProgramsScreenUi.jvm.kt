package com.mindovercnc.linuxcnc.screen.programs.picker.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragData
import androidx.compose.ui.draganddrop.dragData
import androidx.compose.ui.unit.dp
import java.net.URI
import kotlin.io.path.toPath
import okio.Path
import okio.Path.Companion.toOkioPath

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
internal actual fun Modifier.programFileDropTarget(onFileDropped: (Path) -> Unit): Modifier {
    var isDragging by remember { mutableStateOf(false) }
    return then(
            if (isDragging) Modifier.border(width = 1.dp, color = MaterialTheme.colorScheme.primary)
            else Modifier
        )
        .dragAndDropTarget(
            shouldStartDragAndDrop = { event -> event.dragData() is DragData.FilesList },
            target =
                object : DragAndDropTarget {
                    override fun onStarted(event: DragAndDropEvent) {
                        isDragging = true
                    }

                    override fun onEnded(event: DragAndDropEvent) {
                        isDragging = false
                    }

                    override fun onDrop(event: DragAndDropEvent): Boolean {
                        val dragData = (event.dragData() as? DragData.FilesList) ?: return false
                        val files = dragData.readFiles()
                        val file = files.singleOrNull() ?: return false
                        onFileDropped(URI(file).toPath().toOkioPath())
                        return true
                    }
                },
        )
}
