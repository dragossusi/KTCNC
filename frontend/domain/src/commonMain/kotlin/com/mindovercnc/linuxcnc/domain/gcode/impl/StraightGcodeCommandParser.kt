package com.mindovercnc.linuxcnc.domain.gcode.impl

import com.mindovercnc.linuxcnc.actor.PathElement
import com.mindovercnc.linuxcnc.domain.gcode.GcodeCommandParseScope
import com.mindovercnc.linuxcnc.domain.gcode.GcodeCommandParser
import com.mindovercnc.linuxcnc.reader.gcode.GCodeCommand
import com.mindovercnc.model.Point3D

/** [GcodeCommandParser] that handles straight commands. */
object StraightGcodeCommandParser : GcodeCommandParser {
    override fun GcodeCommandParseScope.parse(command: GCodeCommand): PathElement? {
        val args = command.arguments.split(", ")
        // TODO: This creates an issue when rendering arcs, the lates operate in X
        //   and Z
        val current =
            Point3D(
                x = args[0].toFloat(),
                y = args[1].toFloat(),
                z = args[2].toFloat(),
            )

        val element =
            lastPoint?.let { point ->
                PathElement.Line(
                    startPoint = point,
                    endPoint = current,
                    type =
                    when (command.name) {
                        "STRAIGHT_FEED" -> PathElement.Line.Type.Feed
                        else -> PathElement.Line.Type.Traverse
                    }
                )
            }
        return element.also { lastPoint = current }
    }
}
