package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope
import com.mindovercnc.linuxcnc.canvas.CanvasActor

open class LineActor(
    private val start: Offset,
    private val end: Offset,
    private val thickness: Float = 1f,
    private val pathEffect: PathEffect? = null,
    private val strokeCap: StrokeCap = Stroke.DefaultCap,
    private val color: Color? = null,
) : CanvasActor {
    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        drawScope.drawLine(
            start = start,
            end = end,
            color = color ?: contentColor,
            strokeWidth = thickness,
            pathEffect = pathEffect,
            cap = strokeCap
        )
    }
}