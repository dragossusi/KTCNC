package ro.dragossusi.ktcnc.rpc

import kotlinx.rpc.RemoteService
import kotlinx.rpc.annotations.Rpc

@Rpc
interface FileSystemService : RemoteService {
    suspend fun getRoot(): FileResponse

    suspend fun getFile(path: String): FileResponse

    suspend fun getFilesInPath(path: String): List<FileResponse>
}
