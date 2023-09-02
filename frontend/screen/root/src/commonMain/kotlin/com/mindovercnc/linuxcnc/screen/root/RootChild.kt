package com.mindovercnc.linuxcnc.screen.root

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.value.getValue
import com.mindovercnc.linuxcnc.screen.conversational.ConversationalComponent
import com.mindovercnc.linuxcnc.screen.conversational.ui.ConversationalFab
import com.mindovercnc.linuxcnc.screen.conversational.ui.ConversationalScreenUi
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent
import com.mindovercnc.linuxcnc.screen.manual.root.ui.ManualRootScreenUi
import com.mindovercnc.linuxcnc.screen.programs.root.ProgramsRootComponent
import com.mindovercnc.linuxcnc.screen.programs.root.ui.ProgramsRootFab
import com.mindovercnc.linuxcnc.screen.programs.root.ui.ProgramsRootScreenUi
import com.mindovercnc.linuxcnc.screen.status.root.StatusRootComponent
import com.mindovercnc.linuxcnc.screen.status.root.ui.StatusRootScreenUi
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.root.ui.ToolTabsView
import com.mindovercnc.linuxcnc.screen.tools.root.ui.ToolsRootScreenUi

sealed class RootChild(val config: RootComponent.Config) : TitledChild {

    @Composable abstract fun Content(modifier: Modifier)

    @Composable open fun Fab(modifier: Modifier) {}

    @Composable
    override fun Title(modifier: Modifier) {
        Text(toString())
    }

    class Manual(val component: ManualRootComponent) : RootChild(RootComponent.Config.Manual) {
        @Composable
        override fun Content(modifier: Modifier) {
            ManualRootScreenUi(component, modifier = modifier)
        }
    }

    class Conversational(val component: ConversationalComponent) :
        RootChild(RootComponent.Config.Conversational) {
        @Composable
        override fun Content(modifier: Modifier) {
            ConversationalScreenUi(modifier = modifier)
        }

        @Composable
        override fun Fab(modifier: Modifier) {
            ConversationalFab(onClick = {})
        }
    }

    class Programs(val component: ProgramsRootComponent) :
        RootChild(RootComponent.Config.Programs) {
        @Composable
        override fun Content(modifier: Modifier) {
            ProgramsRootScreenUi(component, modifier = modifier)
        }

        @Composable
        override fun Fab(modifier: Modifier) {
            ProgramsRootFab(component, modifier)
        }
    }

    class Tools(val component: ToolsComponent) : RootChild(RootComponent.Config.Tools) {

        @Composable
        override fun Title(modifier: Modifier) {
            val childSlot by component.childSlot.subscribeAsState()
            ToolTabsView(
                modifier = Modifier.width(450.dp).height(48.dp),
                currentTab = childSlot.child!!.instance.config,
                onTabSelected = component::selectTab
            )
        }

        @Composable
        override fun Content(modifier: Modifier) {
            ToolsRootScreenUi(component = component, modifier = modifier)
        }
    }

    class Status(val component: StatusRootComponent) : RootChild(RootComponent.Config.Status) {
        @Composable
        override fun Content(modifier: Modifier) {
            StatusRootScreenUi(component = component, modifier = modifier)
        }
    }
}
