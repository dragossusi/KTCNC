package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import com.mindovercnc.model.PlaneType
import org.jetbrains.skia.Point
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

fun Path.addLine(
    lineElement: PathElement.Line,
    planeType: PlaneType,
    scale: Float = 1f,
) {
    val start = lineElement.startPoint.toPoint(planeType).scale(scale)
    val end = lineElement.endPoint.toPoint(planeType).scale(scale)
    moveTo(start.x, start.y)
    lineTo(end.x, end.y)
}

fun Path.addArc(
    arcElement: PathElement.Arc,
    planeType: PlaneType,
    scale: Float = 1f,
) {
    val start = arcElement.startPoint.toPoint(planeType).scale(scale)
    val end = arcElement.endPoint.toPoint(planeType).scale(scale)
    val center = arcElement.centerPoint.toPoint(planeType).scale(scale)
    val direction = if (arcElement.direction == PathElement.Arc.Direction.Clockwise) 1 else -1

    val radiusLength = center.distanceTo(end)
    val referencePoint = Point(center.x + radiusLength.toFloat(), center.y)
    val startAngle = angleBetweenThreePoints(referencePoint, center, start)
    val endAngle = angleBetweenThreePoints(referencePoint, center, end)

    //    val arcAngle = angleBetweenThreePoints(start, center, end)
    //    println("Arc angle is: $arcAngle")
    //    println("Start angle is: $startAngle")
    //    println("End angle is: $endAngle")

    addArc(
        oval = Rect(center.toOffset(), radiusLength.toFloat()),
        startAngleDegrees = startAngle.toFloat() * direction,
        sweepAngleDegrees = (endAngle - startAngle).toFloat() * direction
    )
}

private fun angleBetweenThreePoints(start: Point, center: Point, end: Point): Double {
    val ab: Double =
        sqrt((start.x - center.x).toDouble().pow(2.0) + (start.y - center.y).toDouble().pow(2.0))
    val ac: Double =
        sqrt((start.x - end.x).toDouble().pow(2.0) + (start.y - end.y).toDouble().pow(2.0))
    val bc: Double =
        sqrt((center.x - end.x).toDouble().pow(2.0) + (center.y - end.y).toDouble().pow(2.0))
    val cosValue = (ab * ab + bc * bc - ac * ac) / (2 * bc * ab)
    return acos(cosValue) * (180 / PI)
}

private fun Point.distanceTo(point: Point) =
    distance(x1 = x.toDouble(), y1 = y.toDouble(), x2 = point.x.toDouble(), y2 = point.y.toDouble())

private fun distance(x1: Double, y1: Double, x2: Double, y2: Double): Double {
    val x = x1 - x2
    val y = y1 - y2
    return sqrt(x * x + y * y)
}
