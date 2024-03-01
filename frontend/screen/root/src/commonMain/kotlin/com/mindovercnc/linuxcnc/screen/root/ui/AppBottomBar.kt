package com.mindovercnc.linuxcnc.screen.root.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.mindovercnc.linuxcnc.screen.root.RootComponent
import com.mindovercnc.linuxcnc.screen.root.child.RootChild
import ktcnc.frontend.screen.root.generated.resources.Res
import ktcnc.frontend.screen.root.generated.resources.manual_tab
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun AppBottomBar(
    tabs: List<RootComponent.Config>,
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
        for (tab in tabs) {
            TabNavigationItem(
                tab = tab,
                badgeValue = badgeValue.invoke(tab),
                enabled = enabled,
                selected = tab == selected.config,
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