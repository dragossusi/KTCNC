package com.mindovercnc.data.linuxcnc

import ro.dragossusi.ktcnc.rpc.FileResponse

interface FileSystemRepository {
    suspend fun getNcRootAppFile(): FileResponse

    suspend fun getFile(path: String): FileResponse

    suspend fun getFilesInPath(path: String): List<FileResponse>

    suspend fun writeProgramLines(lines: List<String>, programName: String)
}