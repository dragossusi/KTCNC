package com.mindovercnc.linuxcnc.screen.manual.turning.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.actor.ArrowTipActor
import com.mindovercnc.linuxcnc.actor.LineActor
import com.mindovercnc.linuxcnc.actor.OffsetPlacement
import com.mindovercnc.linuxcnc.actor.TextActor
import com.mindovercnc.linuxcnc.canvas.Canvas2D
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope
import com.mindovercnc.linuxcnc.canvas.CanvasActor
import com.mindovercnc.linuxcnc.canvas.rotateBy
import org.jetbrains.skia.Paint

private data class JoystickStatusSizes(
    val centerRadius: Float,
    val outerRadius: Float,
    val textRadius: Float,
)

private val defaultColor = Color.DarkGray

@Composable
fun JoystickStatus(
    modifier: Modifier = Modifier.size(80.dp),
    isTaper: Boolean,
) {
    val density = LocalDensity.current

    val sizes = remember(density) {
        with(density) {
            JoystickStatusSizes(
                centerRadius = 10.dp.toPx(),
                outerRadius = 40.dp.toPx(),
                textRadius = 45.dp.toPx(),
            )
        }
    }

    val axisAngle: Float by animateFloatAsState(
        targetValue = if (isTaper) 45f else 0f,
        animationSpec = tween(
            durationMillis = 100,
            easing = LinearEasing
        )
    )

    Canvas2D(modifier = modifier) { drawScope ->
        val center = drawScope.center
        drawScope.drawCircle(
            center = center,
            radius = sizes.centerRadius,
            color = defaultColor
        )

        drawScope.drawCircle(
            center = center,
            radius = sizes.outerRadius,
            color = defaultColor.copy(alpha = 0.2f),
            style = Stroke(width = 1f)
        )

        for (direction in Direction.entries) {
            val actor = AxisDirectionActor(
                direction = direction,
                centerPoint = center,
                length = sizes.outerRadius,
                axisColor = defaultColor
            )
                .rotateBy(angle = axisAngle, pivot = center)

            with(actor) { drawInto(drawScope) }
        }

        val axisTextActor = AxisTextActor(
            centerPoint = center,
            axisColor = defaultColor,
            lineDrawRadius = sizes.outerRadius,
            textDrawRadius = sizes.textRadius
        )
        with(axisTextActor) { drawInto(drawScope) }
    }
}

private enum class Direction(val text: String) {
    XPlus("X+"), XMinus("X-"), ZPlus("Z+"), ZMinus("Z-")
}

private class AxisTextActor(
    centerPoint: Offset,
    axisColor: Color,
    textDrawRadius: Float,
    lineDrawRadius: Float,
) : CanvasActor {

    val actors = mutableListOf<CanvasActor>()

    init {
        val textPaint = Paint().apply {
            color = axisColor.toArgb()
        }

        Direction.entries.forEach {
            val lineEndPoint = when (it) {
                Direction.XMinus -> Offset(centerPoint.x, centerPoint.y - lineDrawRadius)
                Direction.XPlus -> Offset(centerPoint.x, centerPoint.y + lineDrawRadius)
                Direction.ZMinus -> Offset(centerPoint.x - lineDrawRadius, centerPoint.y)
                Direction.ZPlus -> Offset(centerPoint.x + lineDrawRadius, centerPoint.y)
            }

            val textEndPoint = when (it) {
                Direction.XMinus -> Offset(centerPoint.x, centerPoint.y - textDrawRadius)
                Direction.XPlus -> Offset(centerPoint.x, centerPoint.y + textDrawRadius)
                Direction.ZMinus -> Offset(centerPoint.x - textDrawRadius, centerPoint.y)
                Direction.ZPlus -> Offset(centerPoint.x + textDrawRadius, centerPoint.y)
            }

            val offsetPlacement = when (it) {
                Direction.XMinus -> OffsetPlacement.BottomCenter
                Direction.XPlus -> OffsetPlacement.TopCenter
                Direction.ZMinus -> OffsetPlacement.EndCenter
                Direction.ZPlus -> OffsetPlacement.StartCenter
            }

            actors.add(
                LineActor(
                    start = centerPoint,
                    end = lineEndPoint,
                    thickness = 0.3f,
                    color = axisColor.copy(alpha = 0.7f),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f, 3f), 0f),
                    strokeCap = StrokeCap.Round
                )
            )

            actors.add(
                TextActor(
                    text = it.text,
                    offset = textEndPoint,
                    offsetPlacement,
                    paint = textPaint
                )
            )
        }
    }

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        for (actor in actors) {
            with(actor) { drawInto(drawScope) }
        }
    }
}

private class AxisDirectionActor(
    direction: Direction,
    centerPoint: Offset,
    length: Float,
    thickness: Float = 1f,
    axisColor: Color,
    pathEffect: PathEffect? = null,
) : CanvasActor {

    private val actors = mutableListOf<CanvasActor>()

    init {
        val endPoint = when (direction) {
            Direction.XMinus -> Offset(centerPoint.x, centerPoint.y - length)
            Direction.XPlus -> Offset(centerPoint.x, centerPoint.y + length)
            Direction.ZMinus -> Offset(centerPoint.x - length, centerPoint.y)
            Direction.ZPlus -> Offset(centerPoint.x + length, centerPoint.y)
        }

        val tipAngle = when (direction) {
            Direction.XMinus -> 0f
            Direction.XPlus -> 180f
            Direction.ZMinus -> 270f
            Direction.ZPlus -> 90f
        }

        actors.add(
            LineActor(
                start = centerPoint,
                end = endPoint,
                thickness = thickness,
                color = axisColor,
                pathEffect = pathEffect,
                strokeCap = StrokeCap.Round
            )
        )
        actors.add(
            ArrowTipActor(
                tipOffset = endPoint,
                color = axisColor
            ).rotateBy(angle = tipAngle, pivot = endPoint)
        )
    }

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        for (actor in actors) {
            with(actor) { drawInto(drawScope) }
        }
    }
}
