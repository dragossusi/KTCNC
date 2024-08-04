package components.filesystem

import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropTransferAction
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.DragAndDropTransferable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import okio.Path
import java.awt.datatransfer.StringSelection
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
actual fun FileSystemItemView(item: FileSystemItemData, modifier: Modifier) {
    ContextMenuArea(
        items = {
            listOf(ContextMenuItem("Copy", item.onCopy))
        }
    ) {
        ListItem(
            modifier = modifier.clickable(onClick = item.onClick).fileDragSource(),
            colors = ListItemDefaults.colors(containerColor = colorFor(item)),
            headlineContent = {
                Text(
                    textAlign = TextAlign.Left,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    text = item.title
                )
            },
            supportingContent = item.lastModified?.let { lastModified ->
                {
                    Text(
                        textAlign = TextAlign.Left,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                        text = millisToLastModified(lastModified)
                    )
                }
            },
            leadingContent = { FileImage(item) }
        )
    }
}

private val formatter = DateTimeFormatter.ISO_DATE.withZone(ZoneId.systemDefault())

internal fun millisToLastModified(instant: Instant): String {
    return formatter.format(instant.toJavaInstant())
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
private fun Modifier.fileDragSource(path: Path): Modifier = dragAndDropSource(
    drawDragDecoration = {},
) {
    detectDragGestures(
        onDragStart = { offset ->
            startTransfer(
                DragAndDropTransferData(
                    DragAndDropTransferable(
                        FileTransferable()
                    )
        },
        onDrag = { _, _ -> },
    )
}