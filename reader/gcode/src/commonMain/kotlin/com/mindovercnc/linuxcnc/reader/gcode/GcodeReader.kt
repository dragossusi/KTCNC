package com.mindovercnc.linuxcnc.reader.gcode

import okio.Source

interface GcodeReader {
    suspend fun read(source: Source): List<GCodeCommand>
}