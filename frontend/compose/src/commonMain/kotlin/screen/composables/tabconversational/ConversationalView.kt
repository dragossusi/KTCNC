package screen.composables.tabconversational

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.domain.ConversationalUseCase
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.kodein.di.compose.rememberInstance

@Composable
fun ConversationalView(modifier: Modifier, onNewOpClicked: (ConversationalOperation) -> Unit) {
    val useCase: ConversationalUseCase by rememberInstance()

    Surface(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ConversationalOperation.entries.forEach { Operation(it) { onNewOpClicked.invoke(it) } }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Operation(op: ConversationalOperation, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val shape = RoundedCornerShape(8.dp)
    val resource = remember(op) { ConversationalOperationPainter.from(op) }
    Surface(
        modifier =
        modifier
            .clip(shape)
            .clickable(
                interactionSource,
                indication = LocalIndication.current,
                onClick = onClick
            ),
        border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
        shape = shape,
        shadowElevation = 8.dp,
        // interactionSource = interactionSource
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (resource == null) {
                Box(
                    modifier =
                    Modifier.width(100.dp)
                        .height(100.dp)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(6.dp),
                        )
                )
            } else {
                Image(
                    modifier =
                    Modifier.width(100.dp)
                        .height(100.dp)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(6.dp),
                        ),
                    contentDescription = null,
                    painter = painterResource(resource)
                )
            }
            Text(
                text = op.displayableString,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp).height(40.dp)
            )
        }
    }
}
