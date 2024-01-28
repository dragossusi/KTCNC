package com.mindovercnc.linuxcnc.reader.gcode.impl

import com.mindovercnc.linuxcnc.reader.gcode.GCodeCommand
import com.mindovercnc.linuxcnc.reader.gcode.GcodeParser
import com.mindovercnc.linuxcnc.reader.gcode.GcodeReader
import okio.Source
import okio.buffer
import okio.use

/** Implementation for [GcodeReader].*/
internal class GcodeReaderImpl(
    private val parser: GcodeParser
) : GcodeReader {
    override suspend fun read(source: Source): List<GCodeCommand> {
        val commands = mutableListOf<GCodeCommand>()
        source.buffer()
            .use { bufferedSource ->
                while (true) {
                    val line = bufferedSource.readUtf8Line() ?: break
                    val command = parser.parse(line)
                    commands += command
                }
            }
        return commands
    }

}