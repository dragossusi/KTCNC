package numpad

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.runDesktopComposeUiTest
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RoborazziOptions
import com.mindovercnc.linuxcnc.numpad.InputDialogView
import com.mindovercnc.linuxcnc.numpad.NumPadState
import com.mindovercnc.linuxcnc.numpad.data.InputType
import io.github.takahirom.roborazzi.captureRoboImage
import org.junit.Test

@OptIn(ExperimentalRoborazziApi::class)
class InputDialogViewTest {

    private val roborazziOptions =
        RoborazziOptions(
            captureType = RoborazziOptions.CaptureType.Screenshot(),
        )

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test() = runDesktopComposeUiTest {
        val inputType = InputType.CSS
        setContent {
            InputDialogView(
                numPadState =
                NumPadState(
                    initialValue = 0.0,
                    numInputParameters = inputType,
                    inputType = inputType,
                    onSubmitAction = {}
                ),
                onCancel = {},
                onSubmit = {}
            )
        }

        onNode(isDialog())
            .captureRoboImage(
                filePath = "preview/InputDialogView.png",
                roborazziOptions = roborazziOptions
            )
    }
}
