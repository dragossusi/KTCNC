package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope
import com.mindovercnc.linuxcnc.canvas.CanvasActor

class StartDividerActor(
    val startPoint: Offset,
    val radius: Float
) : CanvasActor {

    val drawStartLine = LineActor(
        Offset(x = startPoint.x, y = startPoint.y + radius),
        Offset(x = startPoint.x, y = startPoint.y - radius)
    )

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        with(drawStartLine) { drawInto(drawScope) }
    }
}