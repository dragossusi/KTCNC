package com.mindovercnc.linuxcnc.screen.root.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.mindovercnc.linuxcnc.screen.root.RootComponent
import com.mindovercnc.linuxcnc.screen.root.child.RootChild

private val iconButtonModifier = Modifier.size(48.dp)

@Composable
fun RootUi(root: RootComponent, modifier: Modifier = Modifier) {
    val childStack by root.childStack.subscribeAsState()
    val state by root.state.collectAsState()
    val active = childStack.active.instance
    Scaffold(
        modifier = modifier,
        topBar = { NewTopAppBar(active) },
        bottomBar = {
            // TODO
            AppBottomBar(
                modifier = Modifier.height(60.dp),
                enabled = true, // todo uiState.isBottomBarEnabled,
                selected = active,
                onClick = root::openTab,
                tabs = root.tabs,
                badgeValue = {
                    when (it) {
                        RootComponent.Config.Tools -> {
                            "T${state.currentTool}"
                        }

                        else -> null
                    }
                })
        },
        floatingActionButton = { active.Fab(Modifier) },
    ) { padding ->
        Children(
            stack = childStack,
            modifier = Modifier.padding(padding),
        ) {
            it.instance.Content(Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewTopAppBar(active: RootChild) {
    CenterAlignedTopAppBar(
        title = { active.Title(Modifier) },
        navigationIcon = { active.NavigationIcon(iconButtonModifier) },
        actions = { with(active) { Actions() } },
        modifier = Modifier.shadow(3.dp))
}

@Composable
fun bottomBarColor(selected: Boolean, enabled: Boolean): Color {
    return when {
        selected -> MaterialTheme.colorScheme.primary
        !enabled -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.onPrimaryContainer
    }
}
