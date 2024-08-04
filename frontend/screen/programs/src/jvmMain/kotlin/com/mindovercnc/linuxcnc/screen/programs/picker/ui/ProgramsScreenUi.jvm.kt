package com.mindovercnc.linuxcnc.screen.programs.picker.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.ui.DragData
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.dragData
import java.net.URI
import kotlin.io.path.toPath
import okio.Path
import okio.Path.Companion.toOkioPath

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
internal actual fun Modifier.programFileDropTarget(onFileDropped: (Path) -> Unit): Modifier {
    return dragAndDropTarget(
        shouldStartDragAndDrop = { event -> event.dragData() is DragData.FilesList },
        target =
            object : DragAndDropTarget {
                override fun onDrop(event: DragAndDropEvent): Boolean {
                    val dragData = (event.dragData() as? DragData.FilesList) ?: return false
                    val files = dragData.readFiles()
                    val file = files.singleOrNull() ?: return false
                    onFileDropped(URI(file).toPath().toOkioPath())
                    return true
                }
            }
    )
}
