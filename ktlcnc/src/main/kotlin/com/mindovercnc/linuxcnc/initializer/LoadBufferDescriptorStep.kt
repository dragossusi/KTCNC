package com.mindovercnc.linuxcnc.initializer

import initializer.InitializerStep
import mu.KotlinLogging
import okio.Path

class LoadBufferDescriptorStep(
    private val destFolder: Path,
    private val fileName: String
) : InitializerStep {
    override suspend fun initialise() {
        // Check if the .json file already exists in destination folder.
        val jsonDestinationFile = destFolder.div(fileName).toFile()
        if (!jsonDestinationFile.exists()) {
            LOG.info("Copy $fileName to $destFolder")
            val resourceStream = KtlCncInitializer::class.java.classLoader.getResourceAsStream(fileName)

            requireNotNull(resourceStream) { "$fileName not found" }

            resourceStream.use { inputStream -> inputStream.copyTo(jsonDestinationFile.outputStream()) }
        }
    }

    companion object {
        private val LOG = KotlinLogging.logger("LoadBufferDescriptor")
    }
}