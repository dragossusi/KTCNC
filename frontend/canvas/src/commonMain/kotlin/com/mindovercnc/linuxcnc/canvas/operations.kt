package com.mindovercnc.linuxcnc.canvas

import androidx.compose.ui.geometry.Offset
import org.jetbrains.skia.Point

fun CanvasActor.rotateBy(angle: Float, pivot: Offset? = null): RotateActor {
    return RotateActor(this, angle = angle, pivot = pivot)
}

fun CanvasActor.translateTo(point: Offset): TranslateActor {
    return TranslateActor(this, left = point.x, top = point.y)
}

fun CanvasActor.translateTo(point: Point): TranslateActor {
    return TranslateActor(this, left = point.x, top = point.y)
}

fun CanvasActor.zoomTo(scale: Float, pivot: Offset? = null): ZoomActor {
    return ZoomActor(this, scale = scale, pivot = pivot)
}
