package com.mindovercnc.linuxcnc.screen.tools.add.toolholder.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.tools.model.ToolHolderType
import ktcnc.frontend.screen.tools.generated.resources.*
import ktcnc.frontend.screen.tools.generated.resources.Res
import ktcnc.frontend.screen.tools.generated.resources.multifix_boring
import ktcnc.frontend.screen.tools.generated.resources.multifix_centered
import ktcnc.frontend.screen.tools.generated.resources.multifix_generic
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ToolHolderView(
    type: ToolHolderType,
    onClick: (ToolHolderType) -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val imageResource =
        when (type) {
            ToolHolderType.Generic -> Res.drawable.multifix_generic
            ToolHolderType.Boring -> Res.drawable.multifix_boring
            ToolHolderType.Centered -> Res.drawable.multifix_centered
            ToolHolderType.Parting -> Res.drawable.multifix_parting
        }

    val color =
        if (isSelected) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.secondaryContainer
    Surface(
        modifier = modifier.width(120.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = { onClick(type) },
        color = color,
        shadowElevation = 8.dp
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                painter = painterResource(imageResource),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = type.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
