package com.mindovercnc.linuxcnc.screen.manual.turning.ui

import com.mindovercnc.model.SimpleCycle
import ktcnc.frontend.screen.manual.generated.resources.*
import ktcnc.frontend.screen.manual.generated.resources.Res
import ktcnc.frontend.screen.manual.generated.resources.id_turn
import ktcnc.frontend.screen.manual.generated.resources.od_chamfer
import ktcnc.frontend.screen.manual.generated.resources.od_turn
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

object SimpleCyclePainter {
    @OptIn(ExperimentalResourceApi::class)
    fun from(simpleCycle: SimpleCycle): DrawableResource? = when (simpleCycle) {
        SimpleCycle.Turning -> Res.drawable.od_turn
        SimpleCycle.Boring -> Res.drawable.id_turn
        SimpleCycle.OdChamfer -> Res.drawable.od_chamfer
        SimpleCycle.IdChamfer -> Res.drawable.id_chamfer
        SimpleCycle.OdRadius -> Res.drawable.od_radius
        SimpleCycle.IdRadius -> Res.drawable.id_radius
        SimpleCycle.Facing -> Res.drawable.facing
        SimpleCycle.Threading -> Res.drawable.threading
        SimpleCycle.KeySlot -> Res.drawable.slotting
        SimpleCycle.Drilling -> null
    }
}