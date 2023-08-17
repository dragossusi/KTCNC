package com.mindovercnc.linuxcnc.screen.manual.virtuallimits

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.screen.manual.Manual
import com.mindovercnc.linuxcnc.screen.rememberScreenModel
import com.mindovercnc.linuxcnc.widgets.NumericInputField

class VirtualLimitsScreen : Manual("Virtual Limits") {

    @Composable
    override fun RowScope.Actions() {
        val screenModel = rememberScreenModel<VirtualLimitsScreenModel>()
        val navigator = LocalNavigator.currentOrThrow
        IconButton(
            modifier = iconButtonModifier,
            onClick = {
                screenModel.applyChanges()
                navigator.pop()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "",
            )
        }
    }

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<VirtualLimitsScreenModel>()
        val state by screenModel.state.collectAsState()

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Limit(
                "X-",
                state.xMinusActive,
                state.xMinus,
                activeChange = { screenModel.setXMinusActive(it) },
                onValueChanged = { screenModel.setXMinus(it) }
            ) {
                screenModel.teachInXMinus()
            }
            Limit(
                "X+",
                state.xPlusActive,
                state.xPlus,
                activeChange = { screenModel.setXPlusActive(it) },
                onValueChanged = { screenModel.setXPlus(it) }
            ) {
                screenModel.teachInXPlus()
            }
            Limit(
                "Z-",
                state.zMinusActive,
                state.zMinus,
                activeChange = { screenModel.setZMinusActive(it) },
                onValueChanged = { screenModel.setZMinus(it) }
            ) {
                screenModel.teachInZMinus()
            }
            Limit(
                "Z+",
                state.zPlusActive,
                state.zPlus,
                activeChange = { screenModel.setZPlusActive(it) },
                onValueChanged = { screenModel.setZPlus(it) }
            ) {
                screenModel.teachInZPlus()
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = state.zPlusIsToolRelated,
                    onClick = { screenModel.setZPlusToolRelated(true) }
                )
                Text("Tool Tip Limit")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = state.zPlusIsToolRelated.not(),
                    onClick = { screenModel.setZPlusToolRelated(false) }
                )
                Text("Tailstock Limit")
            }
        }
    }

    @Composable
    private fun Limit(
        axisDirection: String,
        active: Boolean,
        value: Double,
        activeChange: (Boolean) -> Unit,
        onValueChanged: (Double) -> Unit,
        teachIn: () -> Unit
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = active, activeChange)
            Text(modifier = Modifier.padding(start = 8.dp), text = axisDirection)
            NumericInputField(
                numericValue = value.toFixedDigitsString(),
                inputType =
                    when (axisDirection) {
                        "X-" -> InputType.VIRTUAL_LIMIT_X_MINUS
                        "X+" -> InputType.VIRTUAL_LIMIT_X_PLUS
                        "Z-" -> InputType.VIRTUAL_LIMIT_Z_MINUS
                        else -> InputType.VIRTUAL_LIMIT_Z_PLUS
                    },
                modifier = Modifier.width(100.dp).padding(start = 16.dp),
                onValueChanged = { onValueChanged.invoke(it.toDouble()) }
            )

            Button(
                modifier = Modifier.padding(start = 16.dp),
                enabled = active,
                onClick = teachIn
            ) {
                Text("Teach In $axisDirection")
            }
        }
    }
}