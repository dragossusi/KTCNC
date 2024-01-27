package components.breadcrumb

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runDesktopComposeUiTest
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RoborazziOptions
import io.github.takahirom.roborazzi.captureRoboImage
import org.junit.Test

@OptIn(ExperimentalRoborazziApi::class)
class BreadCrumbViewTest {

    private val data = BreadCrumbData(
        "etc/ssh/sshd_config".split("/").map { path ->
            BreadCrumbItemData(path, onClick = {})
        }
    )
    private val roborazziOptions =
        RoborazziOptions(
            captureType = RoborazziOptions.CaptureType.Screenshot(),
        )

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test() = runDesktopComposeUiTest {
        setContent {
            BreadcrumbView(data)
        }

        onRoot()
            .captureRoboImage(
                filePath = "preview/BreadCrumbView.png",
                roborazziOptions = roborazziOptions
            )
    }

}