package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import com.mindovercnc.linuxcnc.canvas.Canvas2DScope
import com.mindovercnc.linuxcnc.canvas.CanvasActor
import org.jetbrains.skia.Font
import org.jetbrains.skia.Paint
import org.jetbrains.skia.TextLine

enum class OffsetPlacement {
    BottomLeft,
    StartCenter,
    EndCenter,
    TopCenter,
    BottomCenter
}

class TextActor(
    private val text: String,
    private val offset: Offset,
    private val offsetPlacement: OffsetPlacement = OffsetPlacement.BottomLeft,
    private val font: Font = SkiKoFonts.default,
    private val paint: Paint = Paint()
) : CanvasActor {
    override fun Canvas2DScope.drawInto(drawScope: DrawScope) {
        drawScope.drawIntoCanvas {
            it.nativeCanvas.apply {
                val textSize = font.size * drawScope.density
                val textLine = TextLine.make(text, font.makeWithSize(textSize))
                val textOffset = when (offsetPlacement) {
                    OffsetPlacement.TopCenter -> Offset(offset.x - textLine.width / 2, offset.y + textLine.height)
                    OffsetPlacement.BottomCenter -> Offset(offset.x - textLine.width / 2, offset.y)
                    OffsetPlacement.StartCenter -> Offset(offset.x, offset.y + textLine.height/2)
                    OffsetPlacement.EndCenter -> Offset(offset.x - textLine.width, offset.y + textLine.height / 2)
                    else -> offset
                }

                drawTextLine(
                    textLine, textOffset.x, textOffset.y, paint
                )
            }
        }
    }
}
