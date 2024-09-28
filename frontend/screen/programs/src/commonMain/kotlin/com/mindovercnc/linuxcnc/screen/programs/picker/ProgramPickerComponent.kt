package com.mindovercnc.linuxcnc.screen.programs.picker

import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import okio.Path
import ro.dragossusi.ktcnc.rpc.FileResponse

interface ProgramPickerComponent : AppScreenComponent<ProgramPickerState> {
    fun showError(error: String)
    fun clearError()
    fun selectItem(file: FileResponse)
    fun loadFolderContents(file: FileResponse)
    fun setCurrentFile(file: Path?)
}