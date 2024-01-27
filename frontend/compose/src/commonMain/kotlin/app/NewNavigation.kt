package app

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.mindovercnc.linuxcnc.screen.root.RootComponent
import com.mindovercnc.linuxcnc.screen.root.child.RootChild
import ktcnc.frontend.compose.generated.resources.Res
import org.jetbrains.compose.resources.*

private val iconButtonModifier = Modifier.size(48.dp)

@Composable
fun NewNavigation(root: RootComponent, modifier: Modifier = Modifier) {
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
                badgeValue = {
                    when (it) {
                        RootComponent.Config.Tools -> {
                            "T${state.currentTool}"
                        }

                        else -> null
                    }
                }
            )
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
        modifier = Modifier.shadow(3.dp)
    )
}

private val tabs: List<RootComponent.Config> =
    listOf(
        RootComponent.Config.Manual,
        RootComponent.Config.Conversational,
        RootComponent.Config.Programs,
        RootComponent.Config.Tools,
        RootComponent.Config.Status,
    )

@Composable
private fun AppBottomBar(
    selected: RootChild,
    onClick: (RootComponent.Config) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    badgeValue: ((RootComponent.Config) -> String?) = { null },
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
    ) {
        tabs.forEach { tab ->
            TabNavigationItem(
                tab = tab,
                badgeValue = badgeValue.invoke(tab),
                enabled = enabled,
                selected = tab == selected,
                onClick = onClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RowScope.TabNavigationItem(
    tab: RootComponent.Config,
    badgeValue: String? = null,
    enabled: Boolean,
    selected: Boolean,
    onClick: (RootComponent.Config) -> Unit
) {
    val tabColor = bottomBarColor(selected, enabled)
    NavigationBarItem(
        label = {
            Text(
                color = tabColor,
                text = tab.toString(),
            )
        },
        enabled = enabled,
        selected = selected,
        onClick = { onClick(tab) },
        icon = {
            if (badgeValue != null) {
                BadgedBox(
                    badge = {
                        Badge(containerColor = MaterialTheme.colorScheme.secondary) {
                            Text(text = badgeValue, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                ) {
                    BottomIcon(tab = tab, tint = tabColor)
                }
            } else {
                BottomIcon(tab = tab, tint = tabColor)
            }
        },
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun BottomIcon(tab: RootComponent.Config, tint: Color, modifier: Modifier = Modifier) {
    val painter =
        when (tab) {
            RootComponent.Config.Conversational -> rememberVectorPainter(Icons.Default.Star)
            RootComponent.Config.Manual -> {
                painterResource(Res.drawable.manual_tab)
            }

            RootComponent.Config.Programs -> rememberVectorPainter(Icons.AutoMirrored.Default.List)
            RootComponent.Config.Status -> rememberVectorPainter(Icons.Default.Info)
            RootComponent.Config.Tools -> rememberVectorPainter(Icons.Default.Build)
        }
    Icon(painter = painter, contentDescription = null, modifier = modifier, tint = tint)
}

@Composable
fun bottomBarColor(selected: Boolean, enabled: Boolean): Color {
    return when {
        selected -> MaterialTheme.colorScheme.primary
        !enabled -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.onPrimaryContainer
    }
}
