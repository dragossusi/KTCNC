package com.mindovercnc.linuxcnc.domain.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntSize
import com.mindovercnc.linuxcnc.actor.*
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope
import com.mindovercnc.linuxcnc.canvas.CanvasActor
import com.mindovercnc.model.MachineLimits
import com.mindovercnc.model.WcsLimits
import org.jetbrains.skia.Point

const val extraAxisLength = 20 // add another 30 px for the tip of the arrow to exit the path
private val xAxisColor = Color(0xFF14B86F)
private val zAxisColor = Color(0xFF4282E6)

val ZoomRange = 0.25..5.0

data class VisualTurningState(
    val machineLimits: MachineLimits = MachineLimits(),
    val wcsPosition: Point = Point.ZERO,
    val toolPosition: Point = Point.ZERO,
    /**
     * this will hold the pixel per unit needed so that the programs fits within the view bounds.
     */
    val defaultPixelsPerUnit: Float = 1f,
    val scale: Float = 1f,
    val translate: Offset = Offset.Zero,
    val viewportSize: IntSize = IntSize.Zero,
    val centerLineActor: CenterLineActor = CenterLineActor(),
    val referenceActor: ReferenceActor = ReferenceActor(),
    val programRulers: ProgramRulers =
        ProgramRulers(
            wcsLimits = machineLimits.toWcsLimits(wcsPosition),
            pixelPerUnit = defaultPixelsPerUnit * scale
        ),
    val pathUiState: PathUiState = PathUiState(),
) {
    val pixelPerUnit: Float = defaultPixelsPerUnit * scale

    val canZoomOut: Boolean
        get() = scale > ZoomRange.start

    val canZoomIn: Boolean
        get() = scale < ZoomRange.endInclusive

    fun copyWithWcs(wcs: OffsetEntry): VisualTurningState {
        val position = Point(wcs.xOffset.toFloat(), wcs.zOffset.toFloat())
        return copy(
            wcsPosition = position,
            referenceActor = referenceActor.copy(text = wcs.coordinateSystem),
            programRulers =
            ProgramRulers(
                wcsLimits = machineLimits.toWcsLimits(position),
                pixelPerUnit = pixelPerUnit
            )
        )
    }
}

data class PathUiState(
    val pathActor: PathActor = PathActor(),
) : CanvasActor {

    private val axesActor: AxesActor =
        AxesActor(
            xAxisLength = pathActor.xPlusExtents + extraAxisLength,
            zAxisLength = pathActor.zPlusExtents + extraAxisLength,
            xColor = xAxisColor,
            zColor = zAxisColor
        )

    fun rescaled(pixelPerUnit: Float) = copy(pathActor = pathActor.rescaled(pixelPerUnit))

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        with(pathActor) { drawInto(drawScope) }
        with(axesActor) { drawInto(drawScope) }
    }

    fun getInitialTranslate(viewportSize: IntSize) =
        pathActor.programData.getInitialTranslate(viewportSize)
}

data class ProgramRulers(
    val wcsLimits: WcsLimits,
    val pixelPerUnit: Float,
) {
    val top: RulerActor =
        RulerActor(
            placement = RulerActor.Placement.Top,
            pixelPerUnit = pixelPerUnit,
            minValue = wcsLimits.zMin,
            maxValue = wcsLimits.zMax,
            lineColor = zAxisColor
        )
    val bottom: RulerActor =
        RulerActor(
            placement = RulerActor.Placement.Bottom,
            pixelPerUnit = pixelPerUnit,
            minValue = wcsLimits.zMin,
            maxValue = wcsLimits.zMax,
            lineColor = zAxisColor
        )
    val left: RulerActor =
        RulerActor(
            placement = RulerActor.Placement.Left,
            pixelPerUnit = pixelPerUnit,
            minValue = wcsLimits.xMin,
            maxValue = wcsLimits.xMax,
            diameterMode = true,
            lineColor = xAxisColor
        )
    val right: RulerActor =
        RulerActor(
            placement = RulerActor.Placement.Right,
            pixelPerUnit = pixelPerUnit,
            minValue = wcsLimits.xMin,
            maxValue = wcsLimits.xMax,
            diameterMode = true,
            lineColor = xAxisColor
        )

    fun rescaled(pixelPerUnit: Float) = copy(pixelPerUnit = pixelPerUnit)
}

fun MachineLimits.toWcsLimits(wcsPosition: Point) =
    WcsLimits(
        xMin = xMin - wcsPosition.x,
        xMax = xMax - wcsPosition.x,
        zMin = zMin - wcsPosition.y,
        zMax = zMax - wcsPosition.y,
    )
