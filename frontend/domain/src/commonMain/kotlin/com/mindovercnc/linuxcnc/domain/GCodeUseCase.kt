package com.mindovercnc.linuxcnc.domain

import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.linuxcnc.actor.PathElement
import com.mindovercnc.linuxcnc.domain.gcode.GcodeCommandParseScope
import com.mindovercnc.linuxcnc.domain.gcode.GcodeCommandParser
import com.mindovercnc.linuxcnc.domain.gcode.impl.GcodeCommandParseScopeImpl
import com.mindovercnc.linuxcnc.gcode.GCodeInterpreterRepository
import com.mindovercnc.linuxcnc.reader.gcode.GCodeCommand
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import okio.Path

class GCodeUseCase(
    private val gCodeInterpreterRepository: GCodeInterpreterRepository,
    private val ioDispatcher: IoDispatcher,
    private val parsers: Map<String, GcodeCommandParser>
) {

    suspend fun getPathElements(file: Path): List<PathElement> =
        withContext(ioDispatcher.dispatcher) {
            buildList {
                val scope = GcodeCommandParseScopeImpl()
                val commands = gCodeInterpreterRepository.parseFile(file)
                for (command in commands) {
                    val element = scope.getElement(command)
                    if (element != null) add(element)
                }
            }
        }

    private fun GcodeCommandParseScope.getElement(command: GCodeCommand): PathElement? {
        val parser = parsers[command.name]
        return if (parser == null) {
            LOG.warn { "No gcode command parsers for ${command.name}" }
            null
        } else {
            with(parser) { parse(command) }
        }
    }

    companion object {
        private val LOG = KotlinLogging.logger("GCodeUseCase")
    }
}
