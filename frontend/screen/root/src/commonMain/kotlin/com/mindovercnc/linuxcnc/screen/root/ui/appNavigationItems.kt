package com.mindovercnc.linuxcnc.screen.root.ui

import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import com.mindovercnc.linuxcnc.screen.root.RootComponent
import com.mindovercnc.linuxcnc.screen.root.child.RootChild
import org.jetbrains.compose.resources.stringResource

internal fun NavigationSuiteScope.appNavigationItems(
    tabs: List<RootComponent.Config>,
    selected: RootChild,
    onClick: (RootComponent.Config) -> Unit,
    enabled: Boolean = true,
    badgeValue: ((RootComponent.Config) -> String?) = { null },
    colors: NavigationSuiteItemColors? = null,
) {
    for (tab in tabs) {
        navItem(
            tab = tab,
            badgeValue = badgeValue.invoke(tab),
            enabled = enabled,
            selected = tab == selected.config,
            onClick = { onClick(tab) },
            colors = colors,
        )
    }
}

private fun NavigationSuiteScope.navItem(
    tab: RootComponent.Config,
    enabled: Boolean,
    selected: Boolean,
    onClick: () -> Unit,
    badgeValue: String? = null,
    colors: NavigationSuiteItemColors? = null,
) {
    item(
        enabled = enabled,
        selected = selected,
        onClick = onClick,
        icon = { NavItemIcon(tab, badgeValue) },
        label = { Text(text = stringResource(tab.label)) },
        colors = colors,
    )
}
