package com.mindovercnc.linuxcnc.screen.notifications.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.domain.model.Message
import com.mindovercnc.linuxcnc.screen.notifications.NotificationsComponent

// TODO
@Composable
fun NotificationsScreen(
    component: NotificationsComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.collectAsState()

    val lastMessage = state.messageList.lastOrNull() ?: return

    Box(modifier = modifier) {
        when (lastMessage.level) {
            Message.Level.WARNING -> {
                Snackbar(
                    modifier = Modifier.padding(8.dp)
                        .wrapContentSize()
                        .align(Alignment.BottomEnd)
                ) { Text(text = "Message: ${lastMessage.text}") }
            }

            Message.Level.ERROR -> {
                AlertDialog(
                    onDismissRequest = { },
                    text = {
                        Text("Message: ${lastMessage.text}")
                    },
                    confirmButton = {},
                    modifier = Modifier.height(200.dp).width(300.dp)
                )
            }

            else -> Unit
        }
    }
}