package com.mindovercnc.linuxcnc.screen.programs.programloaded

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import com.mindovercnc.linuxcnc.domain.model.ActiveCode
import com.mindovercnc.linuxcnc.screen.AppScreenComponent

interface ProgramLoadedComponent : AppScreenComponent<ProgramLoadedState> {
    fun zoomOut()
    fun zoomIn()
    fun zoomBy(factor: Float)
    fun center()
    fun translate(offset: Offset)
    fun setViewportSize(size: IntSize)
    fun runProgram()
    fun stopProgram()
    fun confirmToolChanged()
    fun cancelToolChange()
    fun onActiveCodeClicked(activeCode: ActiveCode)
}