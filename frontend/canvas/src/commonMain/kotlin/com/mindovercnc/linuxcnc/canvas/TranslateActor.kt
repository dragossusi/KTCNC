package com.mindovercnc.linuxcnc.canvas

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate

class TranslateActor(
    private vararg val actors: CanvasActor,
    private val left: Float,
    private val top: Float,
) : CanvasActor {

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        drawScope.translate(left, top) {
            for (actor in actors) {
                with(actor) { drawInto(this@translate) }
            }
        }
    }
}
