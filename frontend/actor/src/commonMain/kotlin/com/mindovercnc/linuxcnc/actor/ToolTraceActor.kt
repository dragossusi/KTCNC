package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope
import com.mindovercnc.linuxcnc.canvas.CanvasActor

class ToolTraceActor(
    private val tracePath: Path,
    private val tracePathColor: Color = Color.DarkGray,
    private val traceThickness: Float = 1f,
) : CanvasActor {

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        drawScope.drawPath(
            path = tracePath,
            color = tracePathColor,
            style = Stroke(width = traceThickness, cap = StrokeCap.Round)
        )
    }
}