package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope
import com.mindovercnc.linuxcnc.canvas.CanvasActor
import org.jetbrains.skia.Paint

data class ReferenceActor(
    private val radius: Float = 10f,
    private val text: String? = null,
    private val textOffset: Offset = Offset.Zero,
    private val thickness: Float = 1f,
    private val centerPoint: Offset = Offset.Zero,
    private val drawColor: Color? = null,
) : CanvasActor {

    private val boundingRect = Rect(centerPoint, radius)

    private val quadrant1 = Path().apply {
        moveTo(centerPoint.x, centerPoint.y)
        lineTo(centerPoint.x + radius, centerPoint.y)
        addArc(boundingRect, 0f, -90f)
        lineTo(centerPoint.x, centerPoint.y)
    }

    private val quadrant2 = Path().apply {
        moveTo(centerPoint.x, centerPoint.y)
        lineTo(centerPoint.x, centerPoint.y - radius)
        addArc(boundingRect, -90f, -90f)
        lineTo(centerPoint.x, centerPoint.y)
    }

    private val quadrant3 = Path().apply {
        moveTo(centerPoint.x, centerPoint.y)
        lineTo(centerPoint.x - radius, centerPoint.y)
        addArc(boundingRect, -180f, -90f)
        lineTo(centerPoint.x, centerPoint.y)
    }

    private val quadrant4 = Path().apply {
        moveTo(centerPoint.x, centerPoint.y)
        lineTo(centerPoint.x, centerPoint.y + radius)
        addArc(boundingRect, -270f, -90f)
        lineTo(centerPoint.x, centerPoint.y)
    }

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        val drawColor = drawColor ?: contentColor
        drawScope.drawCircle(
            color = drawColor,
            radius = radius,
            style = Stroke(width = thickness),
            center = centerPoint

        )
        drawScope.drawPath(quadrant1, drawColor)
        drawScope.drawPath(quadrant3, drawColor)
        text?.let {
            val textOffset = centerPoint
                .plus(Offset(radius, -radius))
                .plus(textOffset)
            val textPaint = Paint().apply {
                color = drawColor.toArgb()
            }
            val textActor = TextActor(
                text = it,
                offset = textOffset,
                paint = textPaint
            )
            with(textActor) { drawInto(drawScope) }
        }
    }
}