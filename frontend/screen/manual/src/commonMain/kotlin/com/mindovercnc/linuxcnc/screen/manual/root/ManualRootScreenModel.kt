package com.mindovercnc.linuxcnc.screen.manual.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent.Child
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent.Config
import com.mindovercnc.linuxcnc.screen.manual.simplecycles.SimpleCyclesComponent
import com.mindovercnc.linuxcnc.screen.manual.simplecycles.SimpleCyclesScreenModel
import com.mindovercnc.linuxcnc.screen.manual.tapersettings.TaperSettingsComponent
import com.mindovercnc.linuxcnc.screen.manual.tapersettings.TaperSettingsScreenModel
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningComponent
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningScreenModel
import com.mindovercnc.linuxcnc.screen.manual.turningsettings.TurningSettingsComponent
import com.mindovercnc.linuxcnc.screen.manual.turningsettings.TurningSettingsScreenModel
import com.mindovercnc.linuxcnc.screen.manual.virtuallimits.VirtualLimitsComponent
import com.mindovercnc.linuxcnc.screen.manual.virtuallimits.VirtualLimitsScreenModel
import com.mindovercnc.model.SimpleCycle
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.subDI

class ManualRootScreenModel(
    private val di: DI,
    componentContext: ComponentContext,
) : ManualRootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val _childStack =
        childStack(
            source = navigation,
            initialConfiguration = Config.Turning,
            childFactory = ::createChild,
            serializer = Config.serializer())

    override val childStack: Value<ChildStack<*, Child>> = _childStack

    override fun openVirtualLimits() {
        navigation.bringToFront(Config.VirtualLimits)
    }

    override fun openSimpleCycles(simpleCycle: SimpleCycle) {
        navigation.bringToFront(Config.SimpleCycles(simpleCycle))
    }

    override fun openTurningSettings() {
        navigation.bringToFront(Config.TurningSettings)
    }

    override fun openTaperSettings() {
        navigation.bringToFront(Config.TaperSettings)
    }

    override fun navigateUp() {
        navigation.pop()
    }

    private fun createChild(config: Config, componentContext: ComponentContext): Child {
        return when (config) {
            Config.Turning -> {
                Child.Turning(manualTurningComponent(componentContext))
            }
            is Config.SimpleCycles -> {
                Child.SimpleCycles(simpleCyclesComponent(config.simpleCycle, componentContext))
            }
            Config.VirtualLimits -> {
                Child.VirtualLimits(virtualLimitsComponent(componentContext))
            }
            Config.TurningSettings -> {
                Child.TurningSettings(turningSettingsComponent(componentContext))
            }
            Config.TaperSettings -> {
                Child.TaperSettings(taperSettingsComponent(componentContext))
            }
        }
    }

    private fun manualTurningComponent(componentContext: ComponentContext): ManualTurningComponent {
        return ManualTurningScreenModel(di, componentContext)
    }

    private fun simpleCyclesComponent(
        simpleCycle: SimpleCycle,
        componentContext: ComponentContext
    ): SimpleCyclesComponent {
        val subDI = subDI(di) { bindProvider { simpleCycle } }
        return SimpleCyclesScreenModel(subDI, componentContext)
    }

    private fun virtualLimitsComponent(componentContext: ComponentContext): VirtualLimitsComponent {
        return VirtualLimitsScreenModel(di, componentContext)
    }

    private fun turningSettingsComponent(
        componentContext: ComponentContext
    ): TurningSettingsComponent {
        return TurningSettingsScreenModel(di, componentContext)
    }

    private fun taperSettingsComponent(componentContext: ComponentContext): TaperSettingsComponent {
        return TaperSettingsScreenModel(di, componentContext)
    }
}
