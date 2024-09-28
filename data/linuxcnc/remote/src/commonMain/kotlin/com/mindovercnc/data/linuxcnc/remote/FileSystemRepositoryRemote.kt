package com.mindovercnc.data.linuxcnc.remote

import com.mindovercnc.data.linuxcnc.FileSystemRepository
import mu.KotlinLogging
import ro.dragossusi.ktcnc.rpc.FileResponse
import ro.dragossusi.ktcnc.rpc.FileSystemService

class FileSystemRepositoryRemote(
    private val fileSystemService: FileSystemService
) : FileSystemRepository {
    override suspend fun getFile(path: String): FileResponse {
        return fileSystemService.getFile(path)
    }

    override suspend fun getNcRootAppFile(): FileResponse {
        return fileSystemService.getRoot()
    }

    override suspend fun getFilesInPath(path: String): List<FileResponse> {
        // TODO
        return fileSystemService.getFilesInPath(path)
    }

    override suspend fun writeProgramLines(lines: List<String>, programName: String) {
        LOG.warn { "Invoked writeProgramLines" }
    }

    companion object {
        private val LOG = KotlinLogging.logger("FileSystemRepositoryRemote")
    }
}