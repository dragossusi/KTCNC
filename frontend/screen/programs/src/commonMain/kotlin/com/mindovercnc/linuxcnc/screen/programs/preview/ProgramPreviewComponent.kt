package com.mindovercnc.linuxcnc.screen.programs.preview

import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import okio.Path

interface ProgramPreviewComponent : AppScreenComponent<ProgramPreviewState> {
    val path: Path
}
