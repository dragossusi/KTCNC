package com.mindovercnc.linuxcnc.screen.manual.turning.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningComponent
import androidx.compose.material3.VerticalDivider

@Composable
fun ManualTurningFooter(
    rootComponent: ManualRootComponent,
    component: ManualTurningComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.state.collectAsState()
    Row(modifier = modifier, horizontalArrangement = Arrangement.Start) {
        Button(
            onClick = {
                component.openNumPad(InputType.WORKPIECE_ZERO_COORDINATE) {
                    component.setWorkpieceZ(it)
                }
            },
        ) {
            Text("Set Z Datum")
        }

        Column {
            Surface(
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
                shadowElevation = 8.dp
            ) {
                val height = 120.dp
                Row(
                    modifier = Modifier.height(height).padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HandWheelStatus(
                        uiModel = state.handWheelsUiModel,
                        modifier = Modifier.size(height)
                    )
                    VerticalDivider()
                    JoystickStatus(
                        modifier = Modifier.size(height),
                        isTaper = state.taperTurningActive
                    )
                    VerticalDivider()
                    TaperStatusView(
                        taperAngle = state.taperTurningAngle,
                        onClick = rootComponent::openTurningSettings,
                        taperTurningActive = state.taperTurningActive,
                        onCheckedChange = component::setTaperTurningActive
                    )
                }
            }
        }
    }
}
