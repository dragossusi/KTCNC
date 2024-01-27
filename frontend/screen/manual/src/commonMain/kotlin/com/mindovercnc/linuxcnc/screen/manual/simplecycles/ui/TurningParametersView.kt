package com.mindovercnc.linuxcnc.screen.manual.simplecycles.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.screen.manual.simplecycles.SimpleCyclesComponent
import com.mindovercnc.model.SimpleCycleParameters
import ktcnc.frontend.screen.manual.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TurningParametersView(
    viewModel: SimpleCyclesComponent,
    parametersState: SimpleCycleParameters.TurningParameters,
    modifier: Modifier = Modifier
) {
    val subscript =
        SpanStyle(
            baselineShift = BaselineShift(-0.3f),
            fontSize = 14.sp, // font size of subscript
        )

    Row(
        modifier = modifier.padding(16.dp),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Image(
                modifier =
                Modifier.height(300.dp)
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(6.dp),
                    ),
                contentDescription = null,
                painter = painterResource(Res.drawable.od_turn_details)
            )
        }
        Spacer(modifier = Modifier.width(1.dp).fillMaxHeight().background(Color.LightGray))
        Column(
            modifier = Modifier.width(400.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CycleParameter(
                parameterAnnotatedLabel =
                buildAnnotatedString {
                    append("X")
                    withStyle(subscript) { append("1") }
                },
                inputType = InputType.X_END,
                value = parametersState.xEnd,
                onValueChange = viewModel::setXEnd,
            )
            CycleParameter(
                parameterAnnotatedLabel =
                buildAnnotatedString {
                    append("Z")
                    withStyle(subscript) { append("1") }
                },
                inputType = InputType.Z_END,
                value = parametersState.zEnd,
                onValueChange = viewModel::setZEnd,
            )
            CycleParameter(
                parameterAnnotatedLabel =
                buildAnnotatedString {
                    append("X")
                    withStyle(subscript) { append("2") }
                },
                inputType = InputType.X_END,
                value = parametersState.xEnd,
                teachInLabel = "TeachIn X",
                onValueChange = viewModel::setXEnd,
                teachInClicked = viewModel::teachInXEnd
            )
            CycleParameter(
                parameterAnnotatedLabel =
                buildAnnotatedString {
                    append("Z")
                    withStyle(subscript) { append("2") }
                },
                inputType = InputType.Z_END,
                value = parametersState.zEnd,
                teachInLabel = "TeachIn Z",
                onValueChange = viewModel::setZEnd,
                teachInClicked = viewModel::teachInZEnd
            )
            CycleParameter(
                parameterLabel = "P",
                inputType = InputType.DOC,
                value = parametersState.doc,
                onValueChange = viewModel::setDoc,
            )
            CycleParameter(
                parameterLabel = "a",
                inputType = InputType.TAPER_ANGLE,
                value = parametersState.taperAngle,
                onValueChange = viewModel::setTaperAngle,
            )
            CycleParameter(
                parameterLabel = "r",
                inputType = InputType.FILLET_RADIUS,
                value = parametersState.filletRadius,
                onValueChange = viewModel::setFilletRadius,
            )
        }
    }
}
