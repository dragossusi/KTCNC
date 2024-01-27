import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.screen.root.createRootComponent
import com.mindovercnc.linuxcnc.screen.root.ui.RootUi
import themes.AppTheme

@Composable
fun ApplicationScope.AppWindow(
    appConfig: AppConfig,
    componentContext: ComponentContext,
) {
    val windowState =
        rememberWindowState(
            width = appConfig.screenSize.width,
            height = appConfig.screenSize.height
        )

    Window(
        onCloseRequest = this::exitApplication,
        title = "MindOverCNC Lathe",
        focusable = false,
        undecorated = !appConfig.topBarEnabled,
        state = windowState
    ) {
        val newDensity = Density(density = appConfig.density.toFloat())
        CompositionLocalProvider(LocalDensity provides newDensity) {
            AppTheme(appConfig.darkMode) {
                val root = createRootComponent(componentContext)
                RootUi(root = root, modifier = Modifier.fillMaxSize())
            }
        }
    }
}

