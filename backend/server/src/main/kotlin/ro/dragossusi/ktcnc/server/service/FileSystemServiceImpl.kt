package ro.dragossusi.ktcnc.server.service

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import ro.dragossusi.ktcnc.rpc.FileResponse
import ro.dragossusi.ktcnc.rpc.FileSystemService
import kotlin.coroutines.CoroutineContext

/** Implementation for [FileSystemService].*/
class FileSystemServiceImpl(
    private val root: Path,
    private val fileSystem: FileSystem,
    override val coroutineContext: CoroutineContext
) : FileSystemService {
    override fun getRoot(): FileResponse {
        return root.toFileResponse()
    }

    override fun getFilesInPath(path: String): List<FileResponse> = fileSystem
        .list(path.toPath())
        .filter { it.isDisplayable() }
        .map { it.toFileResponse() }

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

    private val Path.extension: String
        get() = segments.last().substringAfterLast('.', "")
}
