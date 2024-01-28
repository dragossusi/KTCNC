package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope
import com.mindovercnc.linuxcnc.canvas.CanvasActor
import com.mindovercnc.linuxcnc.canvas.rotateBy

class DiameterInfoActor(
    startPoint: Offset,
    radius: Float
) : CanvasActor {

    private val topOffset = Offset(x = startPoint.x, y = startPoint.y - radius)
    private val bottomOffset = Offset(x = startPoint.x, y = startPoint.y + radius)
    private val textOffset = Offset(x = startPoint.x - 10, y = startPoint.y)

    private val actors = mutableListOf<CanvasActor>()

    init {
        actors.add(ArrowTipActor(tipOffset = topOffset))
        actors.add(LineActor(topOffset, bottomOffset))

        val strDiameter = "\u2300".plus(" ").plus(radius * 2)

        actors.add(TextActor(strDiameter, textOffset).rotateBy(270f, textOffset))
        actors.add(ArrowTipActor(tipOffset = bottomOffset).rotateBy(180f, pivot = bottomOffset))
    }

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        for (actor in actors) {
            with(actor) { drawInto(drawScope) }
        }
    }
}