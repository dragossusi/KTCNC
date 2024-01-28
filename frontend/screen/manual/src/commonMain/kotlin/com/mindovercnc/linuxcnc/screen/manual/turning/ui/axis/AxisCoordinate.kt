package com.mindovercnc.linuxcnc.screen.manual.turning.ui.axis

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import com.mindovercnc.model.CoordinateAxis
import com.mindovercnc.model.CoordinateUiModel

@Composable
fun AxisCoordinate(
    axis: CoordinateAxis,
    uiModel: CoordinateUiModel,
    zeroPosClicked: () -> Unit,
    absRelClicked: () -> Unit,
    toolOffsetsClicked: () -> Unit,
    modifier: Modifier = Modifier,
    isDiameterMode: Boolean = false,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
        Position(
            positionType = PositionType.SECONDARY,
            uiModel = uiModel,
            isDiameterMode = isDiameterMode,
            modifier = Modifier.alignByBaseline()
        )
        AxisLetter(
            axis = axis,
            uiModel = uiModel,
            isDiameterMode = isDiameterMode,
            onClick = toolOffsetsClicked
        )

        Position(
            positionType = PositionType.PRIMARY,
            uiModel = uiModel,
            isDiameterMode = isDiameterMode,
            modifier = Modifier.alignByBaseline()
        )

        Units(units = uiModel.units, modifier = Modifier.alignByBaseline())

        ZeroPos(onClick = zeroPosClicked)

        AbsRel(
            onClick = absRelClicked,
            isIncremental = uiModel.isIncremental,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AxisLetter(
    axis: CoordinateAxis,
    uiModel: CoordinateUiModel,
    isDiameterMode: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.size(60.dp),
        border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
        onClick = onClick,
        shadowElevation = 16.dp
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = uiModel.axis.name, fontSize = 45.sp)
            if (uiModel.axis == CoordinateAxis.X && isDiameterMode) {
                Text(
                    modifier = Modifier.align(Alignment.CenterEnd).padding(4.dp),
                    text = "\u2300",
                    textAlign = TextAlign.Right,
                    fontSize = 20.sp,
                )
            }
        }
    }
}

@Composable
private fun Position(
    positionType: PositionType,
    uiModel: CoordinateUiModel,
    isDiameterMode: Boolean = false,
    modifier: Modifier = Modifier
) {

    val value =
        when (positionType) {
            PositionType.PRIMARY -> uiModel.primaryValue
            PositionType.SECONDARY -> uiModel.secondaryValue
        }
    if (value != null) {
        Text(
            modifier = modifier.width(positionType.width),
            text =
            (value * (if (isDiameterMode) 2 else 1)).toFixedDigitsString(uiModel.displayDigits),
            fontSize = positionType.fontSize,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Thin,
            textAlign = TextAlign.End
        )
    } else {
        Spacer(modifier = modifier.width(positionType.width))
    }
}

@Composable
private fun Units(units: String, modifier: Modifier = Modifier) {
    Text(
        text = units.lowercase(),
        modifier = modifier,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    )
}

@Composable
private fun ZeroPos(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.size(60.dp),
        border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
        onClick = onClick,
        shadowElevation = 16.dp
    ) {
        val color = LocalContentColor.current
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                start =
                Offset(
                    0f + ZeroPosTokens.innerPadding.toPx(),
                    this.size.height -
                            ZeroPosTokens.innerPadding.toPx() -
                            ZeroPosTokens.distanceBetweenLines.toPx() / 2
                ),
                end =
                Offset(
                    this.size.width -
                            ZeroPosTokens.innerPadding.toPx() -
                            ZeroPosTokens.distanceBetweenLines.toPx() / 2,
                    0f + ZeroPosTokens.innerPadding.toPx()
                ),
                color = color,
                cap = ZeroPosTokens.linesCap,
                strokeWidth = ZeroPosTokens.lineThickness.toPx()
            )
            drawLine(
                start =
                Offset(
                    0f +
                            ZeroPosTokens.innerPadding.toPx() +
                            ZeroPosTokens.distanceBetweenLines.toPx() / 2,
                    this.size.height - ZeroPosTokens.innerPadding.toPx()
                ),
                end =
                Offset(
                    this.size.width - ZeroPosTokens.innerPadding.toPx(),
                    0f +
                            ZeroPosTokens.innerPadding.toPx() +
                            ZeroPosTokens.distanceBetweenLines.toPx() / 2
                ),
                color = color,
                cap = ZeroPosTokens.linesCap,
                strokeWidth = ZeroPosTokens.lineThickness.toPx()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AbsRel(onClick: () -> Unit, isIncremental: Boolean, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.size(60.dp),
        onClick = onClick,
        border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
        shadowElevation = 16.dp
    ) {
        val textPadding: Dp = 4.dp
        Box(
            modifier =
            Modifier.drawBehind {
                drawLine(
                    start = Offset(0f, this.size.height),
                    end = Offset(this.size.width, 0f),
                    color = Color.LightGray
                )
            }
        ) {
            val color = LocalContentColor.current
            val absColor = if (!isIncremental) color else color.copy(alpha = 0.2f)
            val incColor = if (isIncremental) color else color.copy(alpha = 0.2f)
            Text(
                text = "ABS",
                color = absColor,
                style = MaterialTheme.typography.bodyMedium,
                modifier =
                Modifier.align(Alignment.TopStart)
                    .padding(start = textPadding, top = textPadding)
            )
            Text(
                text = "INC",
                color = incColor,
                style = MaterialTheme.typography.bodyMedium,
                modifier =
                Modifier.align(Alignment.BottomEnd)
                    .padding(end = textPadding, bottom = textPadding)
            )
        }
    }
}
