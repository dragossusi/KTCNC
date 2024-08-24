package screen.composables.tabconversational

import ktcnc.frontend.compose.generated.resources.*
import ktcnc.frontend.compose.generated.resources.Res
import ktcnc.frontend.compose.generated.resources.id
import ktcnc.frontend.compose.generated.resources.lathe_depth_step
import ktcnc.frontend.compose.generated.resources.od
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

object ConversationalOperationPainter {
    fun from(operation: ConversationalOperation): DrawableResource? = when (operation) {
        ConversationalOperation.OdTurning -> Res.drawable.od
        ConversationalOperation.IdTurning -> Res.drawable.id
        ConversationalOperation.Profiling -> Res.drawable.lathe_depth_step
        ConversationalOperation.Facing -> Res.drawable.facing
        ConversationalOperation.Grooving -> Res.drawable.parting
        ConversationalOperation.Threading -> Res.drawable.threading
        ConversationalOperation.KeySlot -> Res.drawable.slotting
        ConversationalOperation.Drilling -> null
    }
}