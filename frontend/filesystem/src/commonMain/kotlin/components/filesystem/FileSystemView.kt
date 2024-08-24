package components.filesystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import scroll.VerticalScrollbar
import scroll.draggableScroll

@Composable
fun FileSystemView(data: FileSystemData,
                   modifier: Modifier = Modifier) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {
        LazyColumn(modifier = Modifier.draggableScroll(scrollState, scope), state = scrollState) {
            items(data.items) { item ->
                FileSystemItemView(item)
                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd),
            scrollState = scrollState
        )
    }
}
