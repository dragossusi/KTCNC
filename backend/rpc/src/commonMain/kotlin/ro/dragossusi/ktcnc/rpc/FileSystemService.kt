package ro.dragossusi.ktcnc.rpc

interface FileSystemService {
    suspend fun getRoot(): FileResponse

    suspend fun getFile(path: String): FileResponse

    suspend fun getFilesInPath(path: String): List<FileResponse>
}