package com.mindovercnc.linuxcnc.domain.gcode.impl

import com.mindovercnc.linuxcnc.actor.PathElement
import com.mindovercnc.linuxcnc.domain.gcode.GcodeCommandParseScope
import com.mindovercnc.linuxcnc.domain.gcode.GcodeCommandParser
import com.mindovercnc.linuxcnc.reader.gcode.GCodeCommand
import com.mindovercnc.model.Point3D

/** [GcodeCommandParser] that handles arc feed commands. */
object ArcFeedGcodeCommandParser : GcodeCommandParser {
    override fun GcodeCommandParseScope.parse(command: GCodeCommand): PathElement? {
        val args = command.arguments.split(", ")
        val current =
            Point3D(
                x = args[1].toFloat(),
                y = 0f,
                z = args[0].toFloat(),
            )
        val center =
            Point3D(
                x = args[3].toFloat(),
                y = 0f,
                z = args[2].toFloat(),
            )
        val element =
            lastPoint?.let { point ->
                PathElement.Arc(
                    startPoint = point,
                    endPoint = current,
                    centerPoint = center,
                    direction =
                    if (args[4].toInt() > 0) PathElement.Arc.Direction.Clockwise
                    else PathElement.Arc.Direction.CounterClockwise
                )
            }
        return element.apply { lastPoint = current }
    }
}
