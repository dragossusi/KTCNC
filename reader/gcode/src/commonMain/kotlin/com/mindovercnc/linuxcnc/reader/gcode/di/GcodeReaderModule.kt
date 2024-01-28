package com.mindovercnc.linuxcnc.reader.gcode.di

import com.mindovercnc.linuxcnc.reader.gcode.GcodeParser
import com.mindovercnc.linuxcnc.reader.gcode.GcodeReader
import com.mindovercnc.linuxcnc.reader.gcode.impl.GcodeParserImpl
import com.mindovercnc.linuxcnc.reader.gcode.impl.GcodeReaderImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val GcodeReaderModule = DI.Module("gcode_reader") {
    bindProvider<GcodeParser> { GcodeParserImpl() }
    bindProvider<GcodeReader> { GcodeReaderImpl(instance()) }
}