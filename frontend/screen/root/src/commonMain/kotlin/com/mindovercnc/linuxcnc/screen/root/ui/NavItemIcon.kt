package com.mindovercnc.linuxcnc.screen.root.ui

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
import ktcnc.frontend.screen.root.generated.resources.Res
import ktcnc.frontend.screen.root.generated.resources.manual_tab
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun NavItemIcon(
    tab: RootComponent.Config,
    badgeValue: String? = null,
    modifier: Modifier = Modifier,
    tabColor: Color = LocalContentColor.current,
) {
    if (badgeValue != null) {
        BadgedBox(
            badge = {
                Badge(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                    Text(text = badgeValue, style = MaterialTheme.typography.bodyMedium)
                }
            },
            modifier = modifier,
        ) {
            BottomIcon(tab = tab, tint = tabColor)
        }
    } else {
        BottomIcon(tab = tab, tint = tabColor, modifier = modifier)
    }
}

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
