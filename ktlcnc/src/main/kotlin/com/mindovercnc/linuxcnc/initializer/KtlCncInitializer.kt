package com.mindovercnc.linuxcnc.initializer

import initializer.InitializerStep
import mu.KotlinLogging
import okio.Path

class KtlCncInitializer(
    private val destFolder: Path
) : InitializerStep {

    /** Extract .so file into the destination folder */
    override suspend fun initialise() {
        LOG.info("Loading buffer descriptor")
        LoadBufferDescriptorStep(destFolder, bufferDescriptor)

        // System.loadLibrary("linuxcncini");
        System.loadLibrary("nml")
        System.loadLibrary("linuxcnchal")

        LOG.info { "Loading $linuxcncLibName" }
        LoadSoResourceStep(destFolder, linuxcncLibName).initialise()
    }

    companion object {
        private val LOG = KotlinLogging.logger("CncInitializer")
        private const val linuxcncLibName = "libLinuxCNC.so"
        private const val bufferDescriptor = "bufferDescriptor.json"
    }
}
