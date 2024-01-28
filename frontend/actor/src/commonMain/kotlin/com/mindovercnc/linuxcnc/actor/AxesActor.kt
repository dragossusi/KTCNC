package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.toArgb
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope
import com.mindovercnc.linuxcnc.canvas.CanvasActor
import com.mindovercnc.linuxcnc.canvas.rotateBy
import org.jetbrains.skia.Paint

open class AxesActor(
    xAxisLength: Float = 50f,
    zAxisLength: Float = 50f,
    xAxisLabel: String? = "X+",
    zAxisLabel: String? = "Z+",
    thickness: Float = 1f,
    xColor: Color = Color.Green,
    zColor: Color = Color.Blue,
    pathEffect: PathEffect? = null,
) : CanvasActor {

    private val actors = mutableListOf<CanvasActor>()

    init {
        val xEnd = Offset(0f, xAxisLength)
        val zEnd = Offset(zAxisLength, 0f)
        actors.add(
            LineActor(
                start = Offset(0f, 0f),
                end = xEnd,
                thickness = thickness,
                color = xColor,
                pathEffect = pathEffect,
                strokeCap = StrokeCap.Round
            )
        )
        actors.add(
            LineActor(
                start = Offset(0f, 0f),
                end = zEnd,
                thickness = thickness,
                color = zColor,
                pathEffect = pathEffect,
                strokeCap = StrokeCap.Round
            )
        )

        val xTipOffset = xEnd.plus(Offset(0f, thickness * 2))
        val zTipOffset = zEnd.plus(Offset(thickness * 2, 0f))
        actors.add(
            ArrowTipActor(tipOffset = xTipOffset, color = xColor)
                .rotateBy(180f, pivot = xTipOffset)
        )
        actors.add(
            ArrowTipActor(tipOffset = zTipOffset, color = zColor)
                .rotateBy(90f, pivot = zTipOffset)
        )

        xAxisLabel?.let {
            val xTextOffset = xEnd.plus(Offset(5f, 0f))
            val xTextPaint = Paint().apply {
                color = xColor.toArgb()
            }
            actors.add(TextActor(text = it, xTextOffset, paint = xTextPaint))
        }
        zAxisLabel?.let {
            val zTextOffset = zEnd.plus(Offset(-6f, -5f))
            val zTextPaint = Paint().apply {
                color = zColor.toArgb()
            }
            actors.add(TextActor(text = it, zTextOffset, paint = zTextPaint))
        }
    }

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        for (actor in actors) {
            with(actor) { drawInto(drawScope) }
        }
    }
}