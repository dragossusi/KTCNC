package components.filesystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ktcnc.frontend.filesystem.generated.resources.Res
import ktcnc.frontend.filesystem.generated.resources.folder_icon
import ktcnc.frontend.filesystem.generated.resources.gcode
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
expect fun FileSystemItemView(item: FileSystemItemData, modifier: Modifier = Modifier)


@Composable
internal fun FileImage(item: FileSystemItemData) {
    val resourcePath = remember(item.isDirectory) {
        when {
            item.isDirectory -> Res.drawable.folder_icon
            else -> Res.drawable.gcode
        }
    }

    Image(
        modifier = Modifier.width(40.dp).height(40.dp),
        contentDescription = null,
        painter = painterResource(resourcePath)
    )
}

@Composable
internal fun colorFor(item: FileSystemItemData) =
    when {
        item.isDirectory -> MaterialTheme.colorScheme.tertiaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }