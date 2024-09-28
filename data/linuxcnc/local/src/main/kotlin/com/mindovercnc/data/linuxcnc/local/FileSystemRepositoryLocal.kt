package com.mindovercnc.data.linuxcnc.local

import com.mindovercnc.data.linuxcnc.FileSystemRepository
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.model.extension
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import ro.dragossusi.ktcnc.rpc.FileResponse

/** Implementation for [FileSystemRepository]. */
class FileSystemRepositoryLocal(
    ioDispatcher: IoDispatcher,
    private val ncProgramsDir: Path,
    private val fileSystem: FileSystem
) : FileSystemRepository {

    override suspend fun getNcRootAppFile(): FileResponse {
        return ncProgramsDir.toFileResponse()
    }

    override suspend fun getFile(path: String): FileResponse {
        return path.toPath().toFileResponse()
    }

    override suspend fun getFilesInPath(path: String): List<FileResponse> {
        return fileSystem
            .list(path.toPath())
            .filter { it.isDisplayable() }
            .map { item ->
                val metadata = fileSystem.metadata(item)
                FileResponse(
                    name = item.name,
                    isDirectory = metadata.isDirectory,
                    path = item.toString()
                )
            }
    }

    override suspend fun writeProgramLines(lines: List<String>, programName: String) {
        val conversationalFolder = ncProgramsDir.div("conversational")
        if (!fileSystem.exists(conversationalFolder)) {
            fileSystem.createDirectory(conversationalFolder)
        }
        val programFile = conversationalFolder.div(programName)

        fileSystem.write(programFile) { lines.forEach { line -> writeUtf8(line) } }
    }

    private fun Path.toFileResponse(): FileResponse {
        val metadata = fileSystem.metadata(this)
        return FileResponse(
            name = name,
            isDirectory = metadata.isDirectory,
            path = toString()
        )
    }

    private fun Path.isDisplayable(): Boolean {
        val metadata = fileSystem.metadata(this)
        // todo change to real implementation
        val isHidden = false // toFile().isHidden
        return if (metadata.isDirectory) {
            !isHidden
        } else {
            !isHidden && extension.equals("ngc", true)
        }
    }
}
