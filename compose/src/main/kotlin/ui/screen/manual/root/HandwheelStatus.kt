package ui.screen.manual.root

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import canvas.ArrowTipActor
import canvas.CanvasActor
import canvas.LineActor
import canvas.rotateBy
import extensions.toFixedDigitsString

@Composable
fun HandWheelStatus(modifier: Modifier, uiModel: HandWheelsUiModel?) {
    val activeStatus = uiModel?.let { it.active && it.increment > 0 } ?: false
    val activeColor = if (activeStatus) Color.Green else Color.Red

    val wheelModifier = Modifier.size(50.dp)

    Box(modifier = modifier) {
        JogWheel(
            modifier = wheelModifier.align(Alignment.TopStart),
            axisLetter = "X",
            activeColor = activeColor
        )
        JogWheel(
            modifier = wheelModifier.align(Alignment.BottomEnd),
            axisLetter = "Z",
            activeColor = activeColor
        )
        Column(
            modifier = wheelModifier.align(Alignment.TopEnd),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Canvas(modifier = Modifier) {
                JogIncrementActor(
                    centerPoint = this.center
                ).drawInto(this)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = uiModel?.increment?.toDouble()?.toFixedDigitsString(3) ?: "x.xxx",
                style = MaterialTheme.typography.bodyMedium,
                color = activeColor
            )
        }
    }
}

@Composable
private fun JogWheel(modifier: Modifier, axisLetter: String, activeColor: Color) {

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource("hwheel.xml"),
            contentDescription = ""
        )
        Text(
            color = activeColor,
            text = axisLetter,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

private class JogIncrementActor(
    centerPoint: Offset,
    verticalLineHeight: Dp = 16.dp,
    middleSpacing: Dp = 6.dp,
    horizontalLinesLength: Dp = 20.dp,
    color: Color = Color.DarkGray
) : CanvasActor {

    val actors = mutableListOf<CanvasActor>()

    init {
        actors.add(
            //left vertical line
            LineActor(
                start = Offset(x = centerPoint.x - middleSpacing.value / 2, y = centerPoint.y - verticalLineHeight.value / 2),
                end = Offset(x = centerPoint.x - middleSpacing.value / 2, y = centerPoint.y + verticalLineHeight.value / 2),
                color = color,
            )
        )
        actors.add(
            //right vertical line
            LineActor(
                start = Offset(x = centerPoint.x + middleSpacing.value / 2, y = centerPoint.y - verticalLineHeight.value / 2),
                end = Offset(x = centerPoint.x + middleSpacing.value / 2, y = centerPoint.y + verticalLineHeight.value / 2),
                color = color,
            )
        )
        val leftLineStartPoint = Offset(x = centerPoint.x - middleSpacing.value / 2, y = centerPoint.y)
        actors.add(
            //left horizontal line
            LineActor(
                start = leftLineStartPoint,
                end = leftLineStartPoint.copy(x = leftLineStartPoint.x - horizontalLinesLength.value),
                color = color,
            )
        )
        val rightLineStartPoint = Offset(x = centerPoint.x + middleSpacing.value / 2, y = centerPoint.y)
        actors.add(
            //right horizontal line
            LineActor(
                start = rightLineStartPoint,
                end = rightLineStartPoint.copy(x = rightLineStartPoint.x + horizontalLinesLength.value),
                color = color,
            )
        )
        actors.add(
            //left arrow
            ArrowTipActor(
                tipOffset = leftLineStartPoint,
                color = color
            ).rotateBy(90f, pivot = leftLineStartPoint)
        )
        actors.add(
            //right arrow
            ArrowTipActor(
                tipOffset = rightLineStartPoint,
                color = color
            ).rotateBy(-90f, pivot = rightLineStartPoint)
        )
    }

    override fun drawInto(drawScope: DrawScope) {
        actors.forEach { it.drawInto(drawScope) }
    }
}