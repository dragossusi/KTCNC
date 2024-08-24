package com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.ui

import com.mindovercnc.model.TipOrientation
import ktcnc.frontend.screen.tools.generated.resources.*
import ktcnc.frontend.screen.tools.generated.resources.Res
import ktcnc.frontend.screen.tools.generated.resources.position1
import ktcnc.frontend.screen.tools.generated.resources.position2
import ktcnc.frontend.screen.tools.generated.resources.position3
import org.jetbrains.compose.resources.ExperimentalResourceApi

object TipOrientationPainter {
    fun from(tipOrientation: TipOrientation) = when (tipOrientation) {
        TipOrientation.Position1 -> Res.drawable.position1
        TipOrientation.Position2 -> Res.drawable.position2
        TipOrientation.Position3 -> Res.drawable.position3
        TipOrientation.Position4 -> Res.drawable.position4
        TipOrientation.Position5 -> Res.drawable.position5
        TipOrientation.Position6 -> Res.drawable.position6
        TipOrientation.Position7 -> Res.drawable.position7
        TipOrientation.Position8 -> Res.drawable.position8
        TipOrientation.Position9 -> Res.drawable.position9
    }
}