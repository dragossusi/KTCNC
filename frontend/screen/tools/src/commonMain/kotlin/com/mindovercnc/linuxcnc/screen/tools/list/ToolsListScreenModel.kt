package com.mindovercnc.linuxcnc.screen.tools.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.linuxcnc.screen.tools.list.ToolsListComponent.Config
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.CuttingInsertsToolsTab
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.HoldersToolsTab
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.LatheToolsTab
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.ToolsTabItem
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.cuttinginsert.CuttingInsertComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.cuttinginsert.CuttingInsertScreenModel
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.LatheToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.LatheToolsScreenModel
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.toolholder.HoldersToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.toolholder.HoldersToolsScreenModel
import org.kodein.di.DI

class ToolsListScreenModel(private val di: DI, componentContext: ComponentContext) :
    BaseScreenModel<ToolsState>(ToolsState(), componentContext),
    ToolsListComponent,
    ComponentContext by componentContext {

    private val navigation = SlotNavigation<Config>()

    private val _childSlot =
        childSlot(
            source = navigation,
            initialConfiguration = { Config.Holders },
            childFactory = ::createTabItem,
            serializer = Config.serializer()
        )

    override val childSlot: Value<ChildSlot<*, ToolsTabItem>> = _childSlot

    private fun createTabItem(
        config: Config,
        componentContext: ComponentContext,
    ): ToolsTabItem {
        return when (config) {
            Config.CuttingInserts ->
                CuttingInsertsToolsTab(cuttingInsertsComponent(componentContext))
            Config.Holders -> HoldersToolsTab(holdersComponent(componentContext))
            Config.Lathe -> LatheToolsTab(latheComponent(componentContext))
        }
    }

    private fun latheComponent(componentContext: ComponentContext): LatheToolsComponent {
        return LatheToolsScreenModel(di, componentContext)
    }

    private fun cuttingInsertsComponent(
        componentContext: ComponentContext
    ): CuttingInsertComponent {
        return CuttingInsertScreenModel(di, componentContext)
    }

    private fun holdersComponent(componentContext: ComponentContext): HoldersToolsComponent {
        return HoldersToolsScreenModel(di, componentContext)
    }

    override fun selectTab(config: Config) {
        navigation.activate(config)
    }
}
