package com.mindovercnc.linuxcnc.domain

import clipboard.Clipboard
import com.mindovercnc.data.linuxcnc.FileSystemRepository
import components.filesystem.FileSystemData
import components.filesystem.FileSystemItemData
import okio.Path

class FileSystemDataUseCase(
    private val fileSystemRepository: FileSystemRepository
) {

    fun Path.toFileSystemData(onItemClick: (Path) -> Unit): FileSystemData {
        val items = fileSystemRepository.getFilesInPath(this)
            .map { item ->
                FileSystemItemData(
                    title = item.name,
                    isDirectory = item.isDirectory,
                    lastModified = item.lastModified,
                    path = item.path,
                    onClick = { onItemClick(item.path) },
                    onCopy = { Clipboard.write(item.toString()) }
                )
            }
            .sortedWith(compareBy({ it.isDirectory }, { it.title }))
        return FileSystemData(items)
    }

}
