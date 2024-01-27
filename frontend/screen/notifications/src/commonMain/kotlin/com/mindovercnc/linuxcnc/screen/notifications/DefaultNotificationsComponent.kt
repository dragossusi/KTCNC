package com.mindovercnc.linuxcnc.screen.notifications

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.linuxcnc.domain.MessagesUseCase
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.kodein.di.DI
import org.kodein.di.instance

class DefaultNotificationsComponent(
    di: DI,
    componentContext: ComponentContext
) : BaseScreenModel<NotificationsState>(NotificationsState(emptyList()), componentContext),
    NotificationsComponent {
    private val messagesUseCase: MessagesUseCase by di.instance()

    init {
        messagesUseCase.getAllMessages()
            .onEach { messages ->
                mutableState.update {
                    it.copy(messageList = messages)
                }
            }
            .launchIn(coroutineScope)
    }
}