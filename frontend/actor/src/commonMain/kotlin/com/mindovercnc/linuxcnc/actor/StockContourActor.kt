package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope
import com.mindovercnc.linuxcnc.canvas.CanvasActor

class StockContourActor(
    centerLineYCoordinate: Float,
    xStartPoint: Float = 0f,
    stockDiameter: Float,
    stockLength: Float
) : CanvasActor {

    companion object {
        const val thickness = 0.8f
        private const val dash = 15f
        private const val dot = 5f
        private const val space = 8f
        private val stockPath = PathEffect.dashPathEffect(floatArrayOf(dash, space, dot, space, dot, space), 1f)
    }

    private val horizontalLine = LineActor(
        start = Offset(xStartPoint, centerLineYCoordinate + stockDiameter / 2),
        end = Offset(stockLength, centerLineYCoordinate + stockDiameter / 2),
        pathEffect = stockPath,
        thickness = thickness
    )

    private val verticalLine = LineActor(
        start = Offset(stockLength, centerLineYCoordinate),
        end = Offset(stockLength, centerLineYCoordinate + stockDiameter / 2),
        pathEffect = stockPath,
        thickness = thickness
    )

    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        with(horizontalLine) { drawInto(drawScope) }
        with(verticalLine) { drawInto(drawScope) }
    }
}