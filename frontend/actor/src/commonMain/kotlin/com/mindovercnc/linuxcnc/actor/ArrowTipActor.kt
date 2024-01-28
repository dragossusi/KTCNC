package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope
import com.mindovercnc.linuxcnc.canvas.CanvasActor

class ArrowTipActor(
    private val tipOffset: Offset,
    private val height: Float = 12f,
    private val base: Float = height * 3 / 4,
    private val color: Color? = null
) : CanvasActor {

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        val halfWidth = base / 2
        val path = Path().apply {
            moveTo(tipOffset.x, tipOffset.y) // Top
            lineTo((tipOffset.x - halfWidth), (tipOffset.y + height)) // Bottom left
            lineTo((tipOffset.x + halfWidth), (tipOffset.y + height)) // Bottom right
            lineTo(tipOffset.x, tipOffset.y) // Back to Top
            close()
        }
        drawScope.drawPath(path, color ?: contentColor)
    }
}