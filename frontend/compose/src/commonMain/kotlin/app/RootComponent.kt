package app

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mindovercnc.linuxcnc.screen.manual.root.ManualTurningComponent
import com.mindovercnc.linuxcnc.screen.programs.root.ProgramsRootComponent
import com.mindovercnc.linuxcnc.screen.status.root.StatusRootComponent
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun openTab(tab: Config)

    sealed class Child(val config: Config) {

        class Manual(val component: ManualTurningComponent) : Child(Config.Manual)
        class Conversational(
        // TODO: create component
        ) : Child(Config.Conversational)
        class Programs(val component: ProgramsRootComponent) : Child(Config.Programs)
        class Tools(val component: ToolsComponent) : Child(Config.Tools)
        class Status(val component: StatusRootComponent) : Child(Config.Status)
    }

    sealed interface Config : Parcelable {
        @Parcelize data object Manual : Config
        @Parcelize data object Conversational : Config
        @Parcelize data object Programs : Config
        @Parcelize data object Tools : Config
        @Parcelize data object Status : Config
    }
}
