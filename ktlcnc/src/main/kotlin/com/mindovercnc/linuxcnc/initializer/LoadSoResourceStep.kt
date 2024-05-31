package com.mindovercnc.linuxcnc.initializer

import initializer.InitializerStep
import mu.KotlinLogging
import okio.Path

class LoadSoResourceStep(
    private val destFolder: Path,
    private val libName: String
) : InitializerStep {
    override suspend fun initialise() {
        // Check if the .so file already exists in destination folder.
        val libDestinationFile = destFolder.div(libName).toFile()
        if (!libDestinationFile.exists()) {
            LOG.info("Copy $libName to $destFolder")

            val resourceStream = KtlCncInitializer::class.java.classLoader.getResourceAsStream(libName)

            requireNotNull(resourceStream) { "$libName not found" }
                .use { inputStream -> inputStream.copyTo(libDestinationFile.outputStream()) }
        } else {
            LOG.info { "$libName already extracted" }
        }

        // Load the .so file from path in which we extracted it.
        System.load(libDestinationFile.absolutePath)
    }

    companion object {
        private val LOG = KotlinLogging.logger("LoadSoResource")
    }
}