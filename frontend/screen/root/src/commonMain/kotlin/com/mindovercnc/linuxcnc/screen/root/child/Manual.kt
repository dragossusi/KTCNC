package com.mindovercnc.linuxcnc.screen.root.child

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent
import com.mindovercnc.linuxcnc.screen.manual.root.ui.ManualRootActions
import com.mindovercnc.linuxcnc.screen.manual.root.ui.ManualRootScreenUi
import com.mindovercnc.linuxcnc.screen.root.RootComponent

class Manual(val component: ManualRootComponent) : RootChild(RootComponent.Config.Manual) {

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
        ManualRootScreenUi(component, modifier = modifier)
    }

    @Composable
    override fun RowScope.Actions() {
        ManualRootActions(component)
    }

    @Composable
    override fun Title(modifier: Modifier) {
        val childStack by component.childStack.subscribeAsState()
        childStack.active.instance.Title(modifier)
    }
}
