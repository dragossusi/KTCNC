package com.mindovercnc.linuxcnc.screen.manual.turning.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningComponent
import com.mindovercnc.model.SimpleCycle

@Composable
fun ManualTurningDrawerContent(
    rootComponent: ManualRootComponent,
    component: ManualTurningComponent,
) {
    val items = remember { SimpleCycle.entries }

    ModalDrawerSheet {
        Text(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            textAlign = TextAlign.Center,
            text = "Simple Cycles",
            style = MaterialTheme.typography.headlineSmall,
        )
        HorizontalDivider(modifier = Modifier.fillMaxWidth().padding())

        SimpleCyclesGrid(
            items = items,
            onCycleSelected = { simpleCycle ->
                component.setSimpleCyclesDrawerOpen(false)
                rootComponent.openSimpleCycles(simpleCycle)
            },
            contentPadding = PaddingValues(16.dp)
        )
    }
}
