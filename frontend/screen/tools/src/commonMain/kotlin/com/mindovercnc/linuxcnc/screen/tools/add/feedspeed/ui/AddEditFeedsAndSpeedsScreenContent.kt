package com.mindovercnc.linuxcnc.screen.tools.add.feedspeed.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.listitem.DropDownSetting
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.screen.tools.add.feedspeed.AddEditFeedsAndSpeedsComponent
import com.mindovercnc.linuxcnc.screen.tools.add.feedspeed.AddEditFeedsAndSpeedsState
import com.mindovercnc.linuxcnc.widgets.NumericInputWithUnit
import androidx.compose.material3.VerticalDivider
import com.mindovercnc.model.MaterialCategory

@Composable
fun AddEditFeedsAndSpeedsScreenUi(
    component: AddEditFeedsAndSpeedsComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.collectAsState()
    Row(modifier = modifier) {
        Properties(
            state,
            component,
            modifier = Modifier.weight(3f).widthIn(min = 120.dp).padding(horizontal = 16.dp)
        )

        VerticalDivider()

        Column(
            modifier = Modifier.weight(7f).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                text = "Feeds & Speeds",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )

            val itemModifier = Modifier.fillMaxWidth()

            RangeView(
                "DoC (ap)",
                state.minAp.toString(),
                InputType.MIN_AP,
                state.maxAp.toString(),
                InputType.MAX_AP,
                itemModifier
            )
            RangeView(
                "Feed (fn)",
                state.minFn.toString(),
                InputType.MIN_FN,
                state.maxFn.toString(),
                InputType.MAX_FN,
                itemModifier
            )
            RangeView(
                "Speed (vc)",
                state.minVc.toString(),
                InputType.MIN_VC,
                state.maxVc.toString(),
                InputType.MAX_VC,
                itemModifier
            )
        }
    }
}

private val inputModifier = Modifier.width(140.dp)

@Composable
private fun Properties(
    state: AddEditFeedsAndSpeedsState,
    component: AddEditFeedsAndSpeedsComponent,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            text = "Properties",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.materialName,
            onValueChange = {},
            label = { Text("Material") },
            singleLine = true
        )
        DropDownSetting(
            modifier = Modifier.width(300.dp),
            settingName = "Category",
            items = MaterialCategory.entries,
            dropDownWidth = 150.dp,
            selectedItem = state.materialCategory,
            onValueChanged = {}
        )
    }
}

@Composable
private fun RangeView(
    title: String,
    min: String,
    minType: InputType,
    max: String,
    maxType: InputType,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, modifier = Modifier.width(100.dp))

        Text("between")
        NumericInputWithUnit(
            value = min,
            inputType = minType,
            verticalAlignment = Alignment.CenterVertically,
            onValueChanged = {},
            modifier = inputModifier
        )
        Text("and")
        NumericInputWithUnit(
            value = max,
            inputType = maxType,
            verticalAlignment = Alignment.CenterVertically,
            onValueChanged = {},
            modifier = inputModifier
        )
    }
}
