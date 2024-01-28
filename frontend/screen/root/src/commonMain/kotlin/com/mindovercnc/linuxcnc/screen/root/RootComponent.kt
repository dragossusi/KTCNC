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
import org.kodein.di.compose.localDI

interface RootComponent : BackHandlerOwner {
    val tabs: List<Config>
    val childStack: Value<ChildStack<*, RootChild>>

    val state: StateFlow<RootState>

    fun openTab(tab: Config)

    @Serializable
    sealed interface Config {
        @Serializable
        data object Manual : Config
        @Serializable
        data object Conversational : Config
        @Serializable
        data object Programs : Config
        @Serializable
        data object Tools : Config
        @Serializable
        data object Status : Config
    }
}


@Composable
fun createRootComponent(componentContext: ComponentContext): RootComponent {
    val di = localDI()
    return remember { RootScreenModel(di = di, componentContext = componentContext) }
}