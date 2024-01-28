package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.IntSize
import kotlin.math.abs

data class ProgramData(
    val feedPath: Path = Path(),
    val traversePath: Path = Path(),
    val tracePath: Path = Path(),
) {
    data class ProgramSize(
        val width: Float,
        val height: Float
    )

    val programSize
        get() = ProgramSize(
            width = with(feedPath.getBounds()) {
                abs(left) + abs(right)
            },
            height = feedPath.getBounds().bottom //we consider it from center line which is always 0
        )

    val xPlusExtents get() = feedPath.getBounds().bottom

    val zPlusExtents get() = feedPath.getBounds().right

    fun getInitialTranslate(
        viewportSize: IntSize
    ): Offset {
        val freeHorizontalSpace = viewportSize.width - programSize.width
        val xOffset = abs(feedPath.getBounds().left) + freeHorizontalSpace / 2
        val yOffset = (viewportSize.height - programSize.height) / 2
        return Offset(xOffset, yOffset)
    }
}
