package com.mindovercnc.linuxcnc.screen.root.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.root.RootComponent
import com.mindovercnc.linuxcnc.screen.root.child.RootChild

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
                onClick = onClick,
            )
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(
    tab: RootComponent.Config,
    badgeValue: String? = null,
    enabled: Boolean,
    selected: Boolean,
    onClick: (RootComponent.Config) -> Unit,
) {
    val tabColor = bottomBarColor(selected, enabled)
    NavigationBarItem(
        label = { Text(color = tabColor, text = tab.toString()) },
        enabled = enabled,
        selected = selected,
        onClick = { onClick(tab) },
        icon = { NavItemIcon(tab, badgeValue, tabColor = tabColor) },
    )
}
