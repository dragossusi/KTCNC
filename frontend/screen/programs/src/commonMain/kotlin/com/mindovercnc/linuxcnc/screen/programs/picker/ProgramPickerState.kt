package com.mindovercnc.linuxcnc.screen.programs.picker

import androidx.compose.runtime.Stable
import com.mindovercnc.linuxcnc.screen.programs.preview.ProgramPreviewComponent
import components.breadcrumb.BreadCrumbData
import components.filesystem.FileSystemData

@Stable
data class ProgramPickerState(
    val breadCrumbData: BreadCrumbData = BreadCrumbData.Empty,
    val fileSystemData: FileSystemData = FileSystemData.Empty,
    val error: String? = null,
    val preview: ProgramPreviewComponent? = null
)
