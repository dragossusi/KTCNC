package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope
import com.mindovercnc.linuxcnc.canvas.CanvasActor

class ChamferActor(
    val startPoint: Offset,
    val width: Float,
    val startHeight: Float,
    val endHeight: Float
) : CanvasActor {

    val topLine = LineActor(
        Offset(x = startPoint.x, y = startPoint.y - startHeight),
        Offset(x = startPoint.x + width, y = startPoint.y - endHeight)
    )

    val bottomLine = LineActor(
        Offset(x = startPoint.x, y = startPoint.y + startHeight),
        Offset(x = startPoint.x + width, y = startPoint.y + endHeight)
    )

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        with(topLine) { drawInto(drawScope) }
        with(bottomLine) { drawInto(drawScope) }
    }
}