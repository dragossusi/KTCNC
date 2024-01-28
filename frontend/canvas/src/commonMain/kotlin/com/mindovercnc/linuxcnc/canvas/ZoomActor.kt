package com.mindovercnc.linuxcnc.canvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale

class ZoomActor(
    private vararg val actors: CanvasActor,
    private val scale: Float,
    private val pivot: Offset? = null
) : CanvasActor {

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        drawScope.scale(scale, pivot ?: drawScope.drawContext.size.center) {
            for (actor in actors) {
                with(actor) {
                    drawInto(this@scale)
                }
            }
        }
    }
}
