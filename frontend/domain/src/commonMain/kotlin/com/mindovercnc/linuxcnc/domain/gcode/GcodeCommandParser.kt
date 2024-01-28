package com.mindovercnc.linuxcnc.domain.gcode

import com.mindovercnc.linuxcnc.actor.PathElement
import com.mindovercnc.linuxcnc.reader.gcode.GCodeCommand

interface GcodeCommandParser {
    fun GcodeCommandParseScope.parse(command: GCodeCommand): PathElement?
}
