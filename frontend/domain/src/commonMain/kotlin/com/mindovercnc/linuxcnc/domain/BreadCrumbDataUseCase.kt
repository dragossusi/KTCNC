package com.mindovercnc.linuxcnc.domain

import components.breadcrumb.BreadCrumbData
import components.breadcrumb.BreadCrumbItemData
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import ro.dragossusi.ktcnc.rpc.FileResponse

class BreadCrumbDataUseCase constructor(
    private val fileSystem: FileSystem
) {

    fun FileResponse.toBreadCrumbData(onItemClick: (Path) -> Unit): BreadCrumbData {
        val directories = path.toPath().directories()

        return BreadCrumbData(
            directories.map { path ->
                BreadCrumbItemData(
                    title = path.segments.lastOrNull() ?: "/",
                    onClick = { onItemClick(path) }
                )
            }
        )
    }


    private fun Path.directories(): List<Path> {
        val list = mutableListOf<Path>()

        val metadata = fileSystem.metadata(this)
        if (metadata.isDirectory) {
            list += this
        }

        var parent = this.parent
        while (parent != null) {
            list += parent
            parent = parent.parent
        }
        return list.reversed()
    }
}
