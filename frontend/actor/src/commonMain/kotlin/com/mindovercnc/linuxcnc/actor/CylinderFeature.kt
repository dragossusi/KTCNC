package com.mindovercnc.linuxcnc.actor

import androidx.compose.ui.geometry.Offset

sealed class CylinderFeature {
    data class Chamfer(val width: Float) : CylinderFeature()
    data class Radius(val radius: Float, val centerPoint: Offset) : CylinderFeature()
}