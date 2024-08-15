package components.filesystem

import androidx.compose.runtime.Stable
import kotlinx.datetime.Instant
import okio.Path

@Stable
data class FileSystemItemData(
    val title: String,
    val isDirectory: Boolean,
    val lastModified: Instant?,
    val path: Path,
    val onClick: () -> Unit,
    val onCopy: () -> Unit,
)
