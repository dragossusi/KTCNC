package com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.tools.model.ToolType
import ktcnc.frontend.screen.tools.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun ToolTypeView(
    type: ToolType,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: (ToolType) -> Unit
) {
    val imageResource =
        when (type) {
            ToolType.Turning -> Res.drawable.lathe_tool
            ToolType.Boring -> Res.drawable.lathe_tool
            ToolType.Drilling -> Res.drawable.lathe_tool
            ToolType.Reaming -> Res.drawable.lathe_tool
            ToolType.Grooving -> Res.drawable.lathe_tool
            ToolType.Parting -> Res.drawable.lathe_tool
            ToolType.OdThreading -> Res.drawable.lathe_tool
            ToolType.IdThreading -> Res.drawable.lathe_tool
            ToolType.Slotting -> Res.drawable.lathe_tool
        }

    val color =
        if (isSelected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.primaryContainer
    Card(
        modifier = modifier.width(120.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = { onClick(type) },
        colors = CardDefaults.cardColors(containerColor = color)
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
