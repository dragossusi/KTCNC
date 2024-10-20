package com.mindovercnc.data.linuxcnc.remote

import com.mindovercnc.data.linuxcnc.FileSystemRepository
import io.ktor.client.*
import kotlinx.rpc.krpc.ktor.client.rpc
import kotlinx.rpc.krpc.ktor.client.rpcConfig
import kotlinx.rpc.withService
import mu.KotlinLogging
import ro.dragossusi.ktcnc.rpc.FileResponse
import ro.dragossusi.ktcnc.rpc.FileSystemService

class FileSystemRepositoryRemote(
    private val httpClient: HttpClient
) : FileSystemRepository {

    private suspend fun createService() = httpClient.rpc {
        url {
            host = "localhost"
            port = 8080
        }
        rpcConfig {
            serialization {
                json()
            }
        }
    }.withService<FileSystemService>()

    override suspend fun getFile(path: String): FileResponse {
        return createService().getFile(path)
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