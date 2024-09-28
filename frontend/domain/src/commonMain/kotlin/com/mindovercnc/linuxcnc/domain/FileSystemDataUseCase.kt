package com.mindovercnc.linuxcnc.domain

import clipboard.Clipboard
import com.mindovercnc.data.linuxcnc.FileSystemRepository
import components.filesystem.FileSystemData
import components.filesystem.FileSystemItemData
import ro.dragossusi.ktcnc.rpc.FileResponse

class FileSystemDataUseCase(
    private val fileSystemRepository: FileSystemRepository
) {

    suspend fun FileResponse.toFileSystemData(onItemClick: (FileResponse) -> Unit): FileSystemData {
        val items = fileSystemRepository.getFilesInPath(path)
            .map { item ->
                FileSystemItemData(
                    title = item.name,
                    isDirectory = item.isDirectory,
                    // todo add it back when supported by kotlinx io
                    lastModified = null,
                    path = item.path,
                    onClick = { onItemClick(item) },
                    onCopy = { Clipboard.write(item.path) }
                )
            }
            .sortedWith(compareBy({ it.isDirectory }, { it.title }))
        return FileSystemData(items)
    }

}
