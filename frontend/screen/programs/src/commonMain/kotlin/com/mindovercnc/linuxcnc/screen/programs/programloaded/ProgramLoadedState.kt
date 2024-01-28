package com.mindovercnc.linuxcnc.screen.programs.programloaded

import com.mindovercnc.linuxcnc.domain.model.ActiveCode
import com.mindovercnc.linuxcnc.domain.model.VisualTurningState
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ui.MachineStatus
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ui.ToolChangeModel
import com.mindovercnc.model.PositionModel
import editor.EditorState
import okio.Path

data class ProgramLoadedState(
    val editorState: EditorState? = null,
    val positionModel: PositionModel? = null,
    val currentWcs: String = "--",
    val currentFolder: Path? = null,
    val visualTurningState: VisualTurningState = VisualTurningState(),
    val activeCodes: List<ActiveCode> = emptyList(),
    val machineStatus: MachineStatus = MachineStatus(),
    val toolChangeModel: ToolChangeModel? = null,
)