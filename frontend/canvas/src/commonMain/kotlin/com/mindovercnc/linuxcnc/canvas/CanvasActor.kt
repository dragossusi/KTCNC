package com.mindovercnc.linuxcnc.canvas

import androidx.compose.ui.graphics.drawscope.DrawScope

interface CanvasActor {
    fun Canvas2DScope.drawInto(drawScope: DrawScope)
}