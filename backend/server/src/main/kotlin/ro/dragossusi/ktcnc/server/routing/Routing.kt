package ro.dragossusi.ktcnc.server.routing

import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlinx.rpc.serialization.json
import kotlinx.rpc.transport.ktor.server.RPC
import kotlinx.rpc.transport.ktor.server.rpc
import okio.FileSystem
import okio.Path.Companion.toPath
import ro.dragossusi.ktcnc.rpc.FileSystemService
import ro.dragossusi.ktcnc.server.service.FileSystemServiceImpl

fun Application.configureRouting() {
    install(RPC)

    routing {
        rpc("/files") {
            rpcConfig {
                serialization {
                    json()
                }
            }

            registerService<FileSystemService> { context ->
                FileSystemServiceImpl(
                    "/".toPath(),
                    FileSystem.SYSTEM,
                    context
                )
            }
        }
    }
}