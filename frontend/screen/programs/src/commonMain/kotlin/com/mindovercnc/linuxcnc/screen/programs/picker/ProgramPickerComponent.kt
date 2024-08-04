package com.mindovercnc.linuxcnc.screen.programs.picker

import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import okio.Path

interface ProgramPickerComponent : AppScreenComponent<ProgramPickerState> {
    fun showError(error: String)
    fun clearError()
    fun selectItem(item: Path)
    fun loadFolderContents(file: Path)
    fun setCurrentFile(file: Path?)
}