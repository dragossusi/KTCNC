package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sin
import kotlin.math.tan

sealed class InsertShape {
    abstract val path: Path

    data class Rhomb(val angle: Int, val height: Float) : InsertShape() {
        private val sideLength = height / abs(sin(angle.toDouble().toRadians())).toFloat() //hypotenuse
        private val offsetLength = height / abs(tan(angle.toDouble().toRadians())).toFloat() //adjacent

        override val path: Path
            get() = Path().apply {
                moveTo(x = 0f, y = 0f)
                lineTo(x = sideLength, y = 0f)
                lineTo(x = sideLength + offsetLength, y = height)
                lineTo(x = offsetLength, y = height)
                close()
            }
    }

    data class Triangle(val height: Float) : InsertShape() {
        private val eqTriangleSide = height / (sin(60.0.toRadians())).toFloat()

        override val path: Path
            get() = Path().apply {
                moveTo(x = 0f, y = 0f)
                lineTo(x = -eqTriangleSide / 2, y = height)
                lineTo(x = eqTriangleSide / 2, y = height)
                close()
            }
    }

    data class Circle(val radius: Float) : InsertShape() {
        override val path: Path
            get() = Path().apply {
                addOval(Rect(Offset.Zero, radius))
            }
    }

    data class Drill(
        val diameter: Float,
        val visibleLength: Float = diameter * 3,
        val tipAngle: Float = 118f
    ) : InsertShape() {

        val radius = diameter / 2
        private val adjacentLength = radius / abs(tan((tipAngle / 2.toDouble()).toRadians())).toFloat()

        override val path: Path
            get() = Path().apply {
                moveTo(x = 0f, y = 0f)
                lineTo(x = adjacentLength, y = -radius)
                lineTo(x = visibleLength - adjacentLength, y = -radius)
                lineTo(x = visibleLength - adjacentLength, y = radius)
                lineTo(x = adjacentLength, y = radius)
                close()
            }

    }
}

fun Double.toRadians() = this * PI / 180.0