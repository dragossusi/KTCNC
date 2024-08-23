package com.mindovercnc.linuxcnc.screen.programs.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.mindovercnc.linuxcnc.screen.PathSerializer
import com.mindovercnc.linuxcnc.screen.programs.picker.ProgramPickerComponent
import com.mindovercnc.linuxcnc.screen.programs.picker.ProgramPickerScreenModel
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ProgramLoadedComponent
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ProgramLoadedScreenModel
import kotlinx.serialization.Serializable
import okio.Path
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.subDI

class ProgramsRootScreenModel(
    private val di: DI,
    componentContext: ComponentContext,
) : ProgramsRootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val _childStack =
        childStack(
            source = navigation,
            initialConfiguration = Config.Picker,
            childFactory = ::createChild,
            serializer = Config.serializer())

    override val childStack: Value<ChildStack<*, ProgramsRootComponent.Child>> = _childStack

    @OptIn(DelicateDecomposeApi::class)
    override fun openProgram(path: Path) {
        navigation.push(Config.Loaded(path))
    }

    override fun navigateUp() {
        navigation.pop()
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): ProgramsRootComponent.Child =
        when (config) {
            is Config.Loaded -> {
                ProgramsRootComponent.Child.Loaded(loadedComponent(config.path, componentContext))
            }
            Config.Picker -> ProgramsRootComponent.Child.Picker(pickerComponent(componentContext))
        }

    private fun pickerComponent(componentContext: ComponentContext): ProgramPickerComponent {
        return ProgramPickerScreenModel(di, componentContext)
    }

    private fun loadedComponent(
        path: Path,
        componentContext: ComponentContext
    ): ProgramLoadedComponent {
        val subDi = subDI(di.di) { bindProvider { path } }
        return ProgramLoadedScreenModel(subDi, componentContext)
    }

    @Serializable
    sealed interface Config {
        @Serializable data object Picker : Config

        @Serializable
        data class Loaded(@Serializable(with = PathSerializer::class) val path: Path) : Config
    }
}
