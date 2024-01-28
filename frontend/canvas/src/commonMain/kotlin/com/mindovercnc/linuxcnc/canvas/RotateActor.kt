package com.mindovercnc.linuxcnc.canvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate

class RotateActor(
    vararg val actors: CanvasActor,
    val angle: Float,
    val pivot: Offset? = null
) : CanvasActor {

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        drawScope.rotate(angle, pivot ?: drawScope.center) {
            for (actor in actors) {
                with(actor) {
                    drawInto(this@rotate)
                }
            }
        }
    }
}
