package com.mindovercnc.linuxcnc.screen.root.child

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.mindovercnc.linuxcnc.screen.programs.root.ProgramsRootComponent
import com.mindovercnc.linuxcnc.screen.programs.root.ui.ProgramsRootFab
import com.mindovercnc.linuxcnc.screen.programs.root.ui.ProgramsRootScreenUi
import com.mindovercnc.linuxcnc.screen.root.RootComponent

class Programs(private val component: ProgramsRootComponent) :
    RootChild(RootComponent.Config.Programs) {

    @Composable
    override fun NavigationIcon(modifier: Modifier) {
        val childStack by component.childStack.subscribeAsState()
        if (childStack.items.size > 1) {
            IconButton(modifier = modifier, onClick = component::navigateUp) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
        }
    }

    @Composable
    override fun Content(modifier: Modifier) {
        ProgramsRootScreenUi(component, modifier = modifier)
    }

    @Composable
    override fun Fab(modifier: Modifier) {
        ProgramsRootFab(component, modifier)
    }

    @Composable
    override fun Title(modifier: Modifier) {
        Text("Programs")
    }
}
