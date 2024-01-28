package com.mindovercnc.linuxcnc.gcode.local

import com.mindovercnc.linuxcnc.gcode.GCodeInterpreterRepository
import com.mindovercnc.linuxcnc.reader.gcode.GCodeCommand
import okio.Path
import okio.Path.Companion.toPath

val LinuxCncHome = System.getenv("LINUXCNC_HOME").toPath()

/** Implementation for [GCodeInterpreterRepository]. */
class GCodeInterpreterRepositoryLocal(
    private val gcodeReader: ProcessGcodeReader,
) : GCodeInterpreterRepository {

    override suspend fun parseFile(file: Path): List<GCodeCommand> {
        return gcodeReader.readFile(file)
    }
}
