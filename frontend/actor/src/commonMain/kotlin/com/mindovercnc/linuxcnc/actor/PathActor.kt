package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope
import com.mindovercnc.linuxcnc.canvas.CanvasActor
import com.mindovercnc.model.PlaneType
import org.jetbrains.skia.Point

private const val dash = 2f
private const val space = 5f

data class PathActor(
    private val pathElements: List<PathElement> = emptyList(),
    private val pixelPerUnit: Float = 1f,
    private val feedPathColor: Color? = null,
    private val traversePathColor: Color = Color.Green,
    private val feedThickness: Float = 1f,
    private val traverseThickness: Float = 0.3f,
) : CanvasActor {
    private val traverseEffect = PathEffect.dashPathEffect(floatArrayOf(dash, space), 1f)

    val programData: ProgramData = pathElements.toProgramData(pixelPerUnit)
    val programSize: ProgramData.ProgramSize
        get() = programData.programSize

    val xPlusExtents: Float
        get() = programData.xPlusExtents

    val zPlusExtents: Float
        get() = programData.zPlusExtents

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        drawScope.drawPath(
            path = programData.feedPath,
            color = feedPathColor ?: contentColor,
            style = Stroke(width = feedThickness, cap = StrokeCap.Round)
        )
        drawScope.drawPath(
            path = programData.traversePath,
            color = traversePathColor,
            style = Stroke(width = traverseThickness, cap = StrokeCap.Round)
        )
    }

    fun rescaled(pixelPerUnit: Float) = copy(pixelPerUnit = pixelPerUnit)

    private fun List<PathElement>.toProgramData(pixelPerUnit: Float): ProgramData {
        val fp = Path()
        val tp = Path()
        for (element in this) {
            when (element) {
                is PathElement.Line -> {
                    when (element.type) {
                        PathElement.Line.Type.Feed -> {
                            fp.addLine(element, planeType = PlaneType.X_Z, scale = pixelPerUnit)
                        }

                        PathElement.Line.Type.Traverse -> {
                            tp.addLine(element, planeType = PlaneType.X_Z, scale = pixelPerUnit)
                        }
                    }
                }

                is PathElement.Arc -> {
                    fp.addArc(element, planeType = PlaneType.X_Z, scale = pixelPerUnit)
                }
            }
        }
        fp.close()
        tp.close()

        return ProgramData(feedPath = fp, traversePath = tp)
    }

    private fun List<Point>.toPath(pixelPerUnit: Float): Path {
        val path = Path()
        val previousPoint = firstOrNull()
        if (previousPoint != null) {
            with(previousPoint.scale(pixelPerUnit)) { path.moveTo(x, y) }
            this.forEach { with(it.scale(pixelPerUnit)) { path.lineTo(x, y) } }
        }
        return path
    }
}
