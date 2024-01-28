package com.mindovercnc.linuxcnc.gcode.local.di

import com.mindovercnc.linuxcnc.gcode.GCodeInterpreterRepository
import com.mindovercnc.linuxcnc.gcode.local.GCodeInterpreterRepositoryLocal
import com.mindovercnc.linuxcnc.gcode.local.ProcessGcodeReader
import com.mindovercnc.linuxcnc.reader.gcode.di.GcodeReaderModule
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val GCodeLocalModule =
    DI.Module("gcode_local") {
        import(GcodeReaderModule)
        bindSingleton { ProcessGcodeReader(instance(), instance(), instance(), instance(), instance()) }
        bindSingleton<GCodeInterpreterRepository> { GCodeInterpreterRepositoryLocal(instance()) }
    }
