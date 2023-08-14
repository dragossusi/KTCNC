package ui.screen.manual.root

import com.mindovercnc.linuxcnc.numpad.NumPadState
import components.axis.CoordinateAxis
import components.axis.CoordinatesUiData

data class ManualTurningState(
    val axisCoordinates: CoordinatesUiData =
        CoordinatesUiData(
            x = CoordinateUiModel(CoordinateAxis.X, 0.0),
            z = CoordinateUiModel(CoordinateAxis.Z, 0.0),
        ),
    val toolLoaded: Int? = null,
    val spindleUiModel: SpindleUiModel? = null,
    val feedUiModel: FeedUiModel? = null,
    val taperTurningActive: Boolean = false,
    val taperTurningAngle: Double = 45.0,
    val virtualLimitsAvailable: Boolean = false,
    val virtualLimitsUiModel: VirtualLimitsUiModel? = null,
    val handWheelsUiModel: HandWheelsUiModel? = null,
    val simpleCycleUiModel: SimpleCycleUiModel? = null,
    val wcsUiModel: WcsUiModel? = null,
    val numPadState: NumPadState? = null
)