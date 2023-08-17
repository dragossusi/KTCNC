package com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.linuxcnc.screen.rememberScreenModel
import com.mindovercnc.linuxcnc.screen.tools.Tools
import com.mindovercnc.model.CuttingInsert
import org.kodein.di.bindProvider

class AddEditCuttingInsertScreen(
    private val cuttingInsert: CuttingInsert? = null,
    private val onChanges: () -> Unit
) : Tools(createTitle(cuttingInsert)) {

    @Composable
    override fun RowScope.Actions() {
        val screenModel: AddEditCuttingInsertScreenModel =
            when (cuttingInsert) {
                null -> rememberScreenModel()
                else -> rememberScreenModel { bindProvider { cuttingInsert } }
            }

        val navigator = LocalNavigator.currentOrThrow
        IconButton(
            modifier = iconButtonModifier,
            onClick = {
                screenModel.applyChanges()
                onChanges.invoke()
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
        val screenModel: AddEditCuttingInsertScreenModel =
            when (cuttingInsert) {
                null -> rememberScreenModel()
                else -> rememberScreenModel { bindProvider { cuttingInsert } }
            }

        val state by screenModel.state.collectAsState()
        AddEditCuttingInsertScreenContent(screenModel, state)
    }
}

private fun createTitle(cuttingInsert: CuttingInsert?) =
    when (cuttingInsert) {
        null -> "Add Cutting Insert"
        else -> "Edit Cutting Insert #${cuttingInsert.code}"
    }