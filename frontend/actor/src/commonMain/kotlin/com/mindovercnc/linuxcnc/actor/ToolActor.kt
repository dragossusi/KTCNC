package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope
import com.mindovercnc.linuxcnc.canvas.CanvasActor


open class ToolActor(
    private val insertShape: InsertShape,
    private val thickness: Float = 0.3f,
    private val fillColor: Color = Color(0xAAFECB04),
    private val edgeColor: Color = Color.DarkGray.copy(alpha = 0.7f),
) : CanvasActor {

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        drawScope.drawPath(insertShape.path, fillColor)
        drawScope.drawPath(insertShape.path, style = Stroke(width = thickness, cap = StrokeCap.Round), color = edgeColor)
    }
}
