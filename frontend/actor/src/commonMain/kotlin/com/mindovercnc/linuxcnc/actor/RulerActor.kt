package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.toArgb
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope
import com.mindovercnc.linuxcnc.canvas.CanvasActor
import org.jetbrains.skia.Paint

open class RulerActor(
    private val placement: Placement,
    private val pixelPerUnit: Float,
    private val minValue: Double,
    private val maxValue: Double,
    private val diameterMode: Boolean = false,
    private val thickness: Float = 1f,
    private val shortLineLength: Float = 6f,
    private val longLineLength: Float = 12f,
    private val lineColor: Color? = null,
    private val lastTickColor: Color = Color.Red,
) : CanvasActor {

    enum class Placement {
        Top, Bottom, Left, Right
    }

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        val increment = when {
            pixelPerUnit < 4f -> 10
            pixelPerUnit in 4f..20f -> 1
            else -> 1
        }

        //values are in mm, but the range of a tick will be from 10mm to 0.1mm with default being 1mm
        val rangeStart = (minValue).toInt()
        val rangeEnd = (maxValue).toInt()

        for (aValue in 0..rangeEnd step increment) {
            drawTick(
                aValue = aValue,
                increment = increment,
                diameterMode = diameterMode,
                isLast = aValue == rangeEnd,
                drawScope = drawScope
            )
        }

        for (aValue in 0 downTo rangeStart step increment) {
            drawTick(
                aValue = aValue,
                increment = increment,
                diameterMode = diameterMode,
                isLast = aValue == rangeStart,
                drawScope = drawScope
            )
        }
    }

    private fun Canvas2DScope.drawTick(
        aValue: Int,
        increment: Int,
        diameterMode: Boolean,
        isLast: Boolean,
        drawScope: DrawScope
    ) {
        val lineColor = lineColor?:contentColor
        val tickLength = when {
            isLast -> longLineLength
            aValue.mod(10 * increment) == 0 -> longLineLength
            aValue.mod(5 * increment) == 0 -> (longLineLength + shortLineLength) / 2
            else -> shortLineLength
        }

        val tickStartOffset = when (placement) {
            Placement.Top -> Offset(aValue * pixelPerUnit, 0f)
            Placement.Bottom -> Offset(aValue * pixelPerUnit, drawScope.size.height)
            Placement.Left -> Offset(0f, aValue * pixelPerUnit)
            Placement.Right -> Offset(drawScope.size.width, aValue * pixelPerUnit)
        }

        val tickEndOffset = when (placement) {
            Placement.Top -> tickStartOffset.plus(Offset(x = 0f, y = tickLength))
            Placement.Bottom -> tickStartOffset.minus(Offset(x = 0f, y = tickLength))
            Placement.Left -> tickStartOffset.plus(Offset(x = tickLength, y = 0f))
            Placement.Right -> tickStartOffset.minus(Offset(x = tickLength, y = 0f))
        }

        drawScope.drawLine(
            start = tickStartOffset,
            end = tickEndOffset,
            color = if (isLast) lastTickColor else lineColor,
            strokeWidth = thickness,
            cap = StrokeCap.Round
        )
        if (tickLength == longLineLength) {
            val textOffset = getTextOffset(aValue, placement, tickEndOffset)
            val textPaint = Paint().apply {
                color = lineColor.toArgb()
            }
            val value = if (diameterMode) aValue * 2 else aValue
            val textActor = TextActor(
                text = value.toString(),
                offset = textOffset,
                paint = textPaint
            )
            with(textActor) { drawInto(drawScope) }
        }
    }

    private fun getTextOffset(value: Int, placement: Placement, tickEndOffset: Offset): Offset {
        return when (placement) {
            Placement.Top -> {
                val xOffset = -4f * value.toString().length
                tickEndOffset.plus(Offset(x = xOffset, y = 12f))
            }

            Placement.Bottom -> {
                val xOffset = -4f * value.toString().length
                tickEndOffset.plus(Offset(x = xOffset, y = -4f))
            }

            Placement.Left -> {
                tickEndOffset.plus(Offset(x = 4f, y = 4f))
            }

            Placement.Right -> {
                val xOffset = when (value.toString().length) {
                    1 -> 10f
                    2 -> 18f
                    3 -> if (value < 0) 22f else 26f
                    4 -> 30f
                    else -> 0f
                }
                tickEndOffset.minus(Offset(x = xOffset, y = -4f))
            }
        }
    }
}