package com.mindovercnc.linuxcnc.actor

import com.mindovercnc.model.Point3D

sealed class PathElement(
    open val startPoint: Point3D,
    open val endPoint: Point3D,
) {
    data class Line(
        override val startPoint: Point3D,
        override val endPoint: Point3D,
        val type: Type
    ) : PathElement(startPoint, endPoint) {
        enum class Type {
            Traverse,
            Feed
        }
    }

    data class Arc(
        override val startPoint: Point3D,
        override val endPoint: Point3D,
        val centerPoint: Point3D,
        val direction: Direction
    ) : PathElement(startPoint, endPoint) {
        enum class Direction {
            Clockwise,
            CounterClockwise
        }
    }
}
