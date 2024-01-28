package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope
import com.mindovercnc.linuxcnc.canvas.CanvasActor

class CenterLineActor : CanvasActor {

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        val actor = LineActor(
            start = Offset(0f, 0f),
            end = Offset(drawScope.size.width, 0f),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dash, space, dot, space), 1f),
            thickness = thickness
        )
        with(actor) { drawInto(drawScope) }
    }

    companion object {
        const val thickness: Float = 0.3f
        const val dash: Float = 15f
        const val dot: Float = 5f
        const val space: Float = 15f
    }
}