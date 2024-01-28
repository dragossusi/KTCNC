package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Offset
import com.mindovercnc.model.PlaneType
import com.mindovercnc.model.Point3D
import org.jetbrains.skia.Point

fun Point3D.toPoint(planeType: PlaneType): Point = when(planeType) {
    PlaneType.X_Z -> Point(x,z)
    PlaneType.X_Y -> Point(x,y)
}

fun Point.toOffset() = Offset(x, y)

operator fun Point.minus(point: Point) = Point(x = x - point.x, y = y - point.y)
