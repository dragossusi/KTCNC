package com.mindovercnc.linuxcnc.reader.gcode.impl

import com.mindovercnc.linuxcnc.reader.gcode.GCodeCommand
import com.mindovercnc.linuxcnc.reader.gcode.GcodeParser
import mu.KotlinLogging

/** Implementation for [GcodeParser].*/
internal class GcodeParserImpl : GcodeParser {
    override fun parse(line: String): GCodeCommand {
        LOG.debug { "Parsing $line" }
        val id = line.substringBefore("N..... ").trim().toInt()
        // println("\t\tId:\t\t\t\t$id".colored(PrintColor.YELLOW))

        val command = line.substringAfter("N..... ")
        val splitPos = command.indexOf('(')

        val commandName = command.substring(startIndex = 0, endIndex = splitPos)
        // println("\t\tCommandName:\t$commandName".colored(PrintColor.YELLOW))

        val arguments = command.substring(splitPos + 1, command.lastIndex)
        /*
        if (arguments.isNotEmpty()) {
            println("\t\tArguments:\t\t${arguments}".colored(PrintColor.YELLOW))
        }
        */

        return GCodeCommand(id, commandName, arguments, line).also { gCodeCommand ->
            LOG.debug { "Parsed as $gCodeCommand" }
        }
    }

    private companion object {
        val LOG = KotlinLogging.logger("GcodeParser")
    }
}
