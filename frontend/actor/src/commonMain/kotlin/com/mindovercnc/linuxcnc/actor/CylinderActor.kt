package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope

class CylinderActor(
    val startPoint: Offset,
    val radius: Float,
    val length: Float
) : ShapeActor {

    private val horizontalLineTop = LineActor(
        start = Offset(startPoint.x, startPoint.y + radius),
        end = Offset(startPoint.x + length, startPoint.y + radius),
    )

    private val horizontalLineBottom = LineActor(
        start = Offset(startPoint.x, startPoint.y - radius),
        end = Offset(startPoint.x + length, startPoint.y - radius),
    )

    val diameterInfo = DiameterInfoActor(
        startPoint = Offset(x = startPoint.x + length / 2, y = startPoint.y),
        radius = radius
    )

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        with(horizontalLineTop) { drawInto(drawScope) }
        with(horizontalLineBottom) { drawInto(drawScope) }
        with(diameterInfo) { drawInto(drawScope) }
    }
}