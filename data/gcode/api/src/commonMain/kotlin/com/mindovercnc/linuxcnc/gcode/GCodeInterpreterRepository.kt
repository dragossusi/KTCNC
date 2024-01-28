package com.mindovercnc.linuxcnc.gcode

import com.mindovercnc.linuxcnc.reader.gcode.GCodeCommand
import okio.Path

interface GCodeInterpreterRepository {
    suspend fun parseFile(file: Path): List<GCodeCommand>
}