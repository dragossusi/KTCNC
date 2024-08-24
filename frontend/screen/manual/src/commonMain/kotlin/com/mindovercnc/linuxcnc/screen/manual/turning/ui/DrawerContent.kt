package com.mindovercnc.linuxcnc.screen.manual.turning.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.SimpleCycle
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import scroll.draggableScroll

@Composable
fun SimpleCyclesGrid(
    items: List<SimpleCycle>,
    onCycleSelected: (SimpleCycle) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val scrollState = rememberLazyGridState()
    val scope = rememberCoroutineScope()

    LazyVerticalGrid(
        modifier = modifier.draggableScroll(scrollState, scope),
        state = scrollState,
        contentPadding = contentPadding,
        columns = GridCells.Adaptive(128.dp),
    ) {
        items(items.size) { index ->
            Cycle(
                op = items[index],
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
            ) {
                onCycleSelected.invoke(items[index])
            }
        }
    }
}

@Composable
private fun Cycle(op: SimpleCycle, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            val imageSize = 100.dp
            val image = remember(op) {
                SimpleCyclePainter.from(op)
            }
            val imageModifier = Modifier.size(imageSize)
            if (image == null) {
                Box(modifier = imageModifier)
            } else {
                Image(
                    modifier = imageModifier,
                    contentDescription = null,
                    painter = painterResource(image)
                )
            }
            Text(
                text = op.displayableString,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
