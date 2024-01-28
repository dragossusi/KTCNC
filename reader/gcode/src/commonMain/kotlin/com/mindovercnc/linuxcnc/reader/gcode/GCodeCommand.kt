package com.mindovercnc.linuxcnc.reader.gcode

data class GCodeCommand(
    val id: Int,
    val name: String,
    val arguments: String,
    val rawLine: String
)