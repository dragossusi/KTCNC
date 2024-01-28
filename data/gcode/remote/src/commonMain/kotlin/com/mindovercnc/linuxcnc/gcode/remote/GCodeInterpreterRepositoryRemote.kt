package com.mindovercnc.linuxcnc.gcode.remote

import com.mindovercnc.linuxcnc.gcode.GCodeInterpreterRepository
import com.mindovercnc.linuxcnc.reader.gcode.GCodeCommand
import mu.KotlinLogging
import okio.Path

/** Implementation for [GCodeInterpreterRepository]. */
class GCodeInterpreterRepositoryRemote : GCodeInterpreterRepository {

    init {
        LOG.warn { "Remote implementation is missing" }
    }

    override suspend fun parseFile(file: Path): List<GCodeCommand> {
        return emptyList()
    }

    companion object {
        private val LOG = KotlinLogging.logger("GCodeRepository")
    }
}
