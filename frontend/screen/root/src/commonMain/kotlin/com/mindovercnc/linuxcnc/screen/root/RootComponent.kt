package com.mindovercnc.linuxcnc.screen.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.mindovercnc.linuxcnc.screen.root.child.RootChild
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import ktcnc.frontend.screen.root.generated.resources.*
import org.jetbrains.compose.resources.StringResource
import org.kodein.di.compose.localDI

interface RootComponent : BackHandlerOwner {
    val tabs: List<Config>
    val childStack: Value<ChildStack<*, RootChild>>

    val state: StateFlow<RootState>

    fun openTab(tab: Config)

    @Serializable
    sealed interface Config {
        val label: StringResource

        @Serializable
        data object Manual : Config {
            override val label: StringResource
                get() = Res.string.root_navigation_tab_manual
        }

        @Serializable
        data object Conversational : Config {
            override val label: StringResource
                get() = Res.string.root_navigation_tab_conversational
        }

        @Serializable
        data object Programs : Config {
            override val label: StringResource
                get() = Res.string.root_navigation_tab_programs
        }

        @Serializable
        data object Tools : Config {
            override val label: StringResource
                get() = Res.string.root_navigation_tab_tools
        }

        @Serializable
        data object Status : Config {
            override val label: StringResource
                get() = Res.string.root_navigation_tab_status
        }
    }
}

@Composable
fun createRootComponent(componentContext: ComponentContext): RootComponent {
    val di = localDI()
    return remember { RootScreenModel(di = di, componentContext = componentContext) }
}
