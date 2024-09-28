package ro.dragossusi.ktcnc.rpc

import kotlinx.rpc.RPC

interface FileSystemService : RPC {
//    suspend fun getRoot(): FileResponse

    suspend fun getFilesInPath(path: String): List<FileResponse>
}