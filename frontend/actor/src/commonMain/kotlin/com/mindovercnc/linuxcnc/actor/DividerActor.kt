package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope
import com.mindovercnc.linuxcnc.canvas.CanvasActor
import kotlin.math.max

class DividerActor(
    val startPoint: Offset,
    val startRadius: Float,
    val endRadius: Float
) : CanvasActor {

    val drawTopLine = LineActor(
        startPoint,
        Offset(x = startPoint.x, y = startPoint.y - max(startRadius, endRadius))
    )

    val drawBottomLine = LineActor(
        Offset(x = startPoint.x, y = startPoint.y + startRadius),
        Offset(x = startPoint.x, y = startPoint.y + endRadius),
    )

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        with(drawTopLine) { drawInto(drawScope) }
        with(drawBottomLine) { drawInto(drawScope) }
    }
}