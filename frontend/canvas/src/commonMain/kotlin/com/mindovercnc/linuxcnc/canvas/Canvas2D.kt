package com.mindovercnc.linuxcnc.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.DrawScope

@Composable
fun Canvas2D(modifier: Modifier = Modifier, content: Canvas2DScope.(DrawScope) -> Unit) {
    val color = LocalContentColor.current

    val scope = remember(color) { Canvas2DScopeImpl(color) }
    Canvas(modifier) { scope.content(this) }
}