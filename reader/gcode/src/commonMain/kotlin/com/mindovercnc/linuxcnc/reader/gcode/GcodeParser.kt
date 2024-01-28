package com.mindovercnc.linuxcnc.reader.gcode

interface GcodeParser {
    fun parse(line: String): GCodeCommand
}