package com.mindovercnc.linuxcnc.screen.programs.programloaded.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.translate
import com.mindovercnc.linuxcnc.actor.InsertShape
import com.mindovercnc.linuxcnc.actor.ToolActor
import com.mindovercnc.linuxcnc.actor.minus
import com.mindovercnc.linuxcnc.canvas.Canvas2D
import com.mindovercnc.linuxcnc.canvas.rotateBy
import com.mindovercnc.linuxcnc.canvas.translateTo
import com.mindovercnc.linuxcnc.domain.model.VisualTurningState


@Composable
fun VisualTurning(
    state: VisualTurningState,
    modifier: Modifier = Modifier
) {


//    val insertShape = InsertShape.Drill(
//        diameter = 2.5f * state.pixelPerUnit,
//    )

    val toolActor = remember(state) {
        val insertShape =
            InsertShape.Rhomb(
                angle = 55,
                height = state.pixelPerUnit * 1f
            )

        ToolActor(insertShape = insertShape)
            .translateTo(state.translate)
            .rotateBy(30f, state.translate)
            .translateTo(state.toolPosition.minus(state.wcsPosition).scale(state.pixelPerUnit))
    }

    Canvas2D(modifier.fillMaxSize()) { drawScope ->
        //println("--Pixel per unit: ${state.pixelPerUnit}")
        drawScope.clipRect {
            translate(left = state.translate.x, top = state.translate.y) {
                with(state.pathUiState) { drawInto(this@translate) }
                with(state.referenceActor) { drawInto(this@translate) }
            }

            translate(left = state.translate.x, top = 0f) {
                with(state.programRulers.top) { drawInto(this@translate) }
                with(state.programRulers.bottom) { drawInto(this@translate) }
            }

            translate(left = 0f, top = state.translate.y) {
                with(state.centerLineActor) { drawInto(this@translate) }
                with(state.programRulers.left) { drawInto(this@translate) }
                with(state.programRulers.right) { drawInto(this@translate) }
            }

            with(toolActor) { drawInto(this@clipRect) }
        }
    }
}
