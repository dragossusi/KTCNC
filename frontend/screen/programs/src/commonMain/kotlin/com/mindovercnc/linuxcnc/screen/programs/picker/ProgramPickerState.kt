package com.mindovercnc.linuxcnc.screen.programs.picker

import androidx.compose.runtime.Stable
import components.breadcrumb.BreadCrumbData
import components.filesystem.FileSystemData
import editor.EditorState
import okio.Path

@Stable
data class ProgramPickerState(
    val breadCrumbData: BreadCrumbData = BreadCrumbData.Empty,
    val fileSystemData: FileSystemData = FileSystemData.Empty,
    val editorState: EditorState? = null,
    val error: String? = null
)
