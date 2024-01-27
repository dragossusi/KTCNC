package components.breadcrumb

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import scroll.draggableScroll

private val trapezeShape = TrapezeShape()
private val parallelogramShape = ParallelogramShape()

@Composable
fun BreadcrumbView(
    data: BreadCrumbData,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LazyRow(
        modifier = modifier.draggableScroll(scrollState, scope, Orientation.Horizontal),
        state = scrollState,
        contentPadding = contentPadding
    ) {
        itemsIndexed(data.items) { index, item ->
            BreadcrumbItem(
                shape = if (index == 0) trapezeShape else parallelogramShape,
                item = item,
                selected = index == data.items.lastIndex
            )
        }
    }
}

@Composable
internal fun BreadcrumbItem(
    shape: Shape,
    item: BreadCrumbItemData,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val color =
        if (selected) MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.surface

    Surface(
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        shape = shape,
        onClick = item.onClick,
        color = color
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Text(text = item.title.ifEmpty { "root" })
        }
    }
}
