package com.mindovercnc.linuxcnc.domain

import com.mindovercnc.data.lathehal.HalRepository
import com.mindovercnc.data.lathehal.model.JoystickStatus
import com.mindovercnc.data.lathehal.model.SpindleSwitchStatus
import com.mindovercnc.data.linuxcnc.CncCommandRepository
import com.mindovercnc.data.linuxcnc.CncStatusRepository
import com.mindovercnc.data.linuxcnc.IniFileRepository
import com.mindovercnc.data.linuxcnc.model.G53AxisLimits
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.linuxcnc.settings.SettingsRepository
import com.mindovercnc.model.CncStateMessage
import com.mindovercnc.repository.CncMessagesRepository
import com.mindovercnc.repository.MotionStatusRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ro.dragossusi.proto.linuxcnc.CncStatus
import ro.dragossusi.proto.linuxcnc.status.MotionStatus
import ro.dragossusi.proto.linuxcnc.status.SpindleDirection
import ro.dragossusi.proto.linuxcnc.status.SpindleStatus

// TODO fix this
/** Test for [ManualTurningUseCase]. */
@OptIn(ExperimentalCoroutinesApi::class)
class ManualTurningUseCaseTest {
    private val joystickStatusFlow =
        MutableStateFlow(JoystickStatus(JoystickStatus.Position.Neutral))
    private val spindleSwitchStatusFlow = MutableStateFlow(SpindleSwitchStatus.NEUTRAL)
    private val cycleStopStatusFlow = MutableStateFlow(false)
    private val cycleStartStatusFlow = MutableStateFlow(false)
    private val actualSpindleSpeed = MutableStateFlow(1f)
    private val jogIncrementValue = MutableStateFlow(1f)
    private val cncStatusFlowMock = MutableSharedFlow<CncStatus>()

    private val motionStatusFlowMock =
        MutableStateFlow(
            MotionStatus(
                spindle_status = listOf(SpindleStatus(direction = SpindleDirection.REVERSE))
            )
        )

    private val statusRepository =
        mockk<CncStatusRepository> { every { cncStatusFlow } returns cncStatusFlowMock }
    private val commandRepository = mockk<CncCommandRepository>()
    private val messagesRepository = mockk<CncMessagesRepository>(relaxUnitFun = true)
    private val halRepository =
        mockk<HalRepository>(relaxUnitFun = true) {
            every { getJoystickStatus() } returns joystickStatusFlow
            every { getSpindleSwitchStatus() } returns spindleSwitchStatusFlow
            every { getCycleStopStatus() } returns cycleStopStatusFlow
            every { getCycleStartStatus() } returns cycleStartStatusFlow
            every { actualSpindleSpeed() } returns actualSpindleSpeed
            every { jogIncrementValue() } returns jogIncrementValue
        }
    private val settingsRepository = mockk<SettingsRepository>()
    private val iniFileRepository =
        mockk<IniFileRepository> { every { getActiveLimits() } returns G53AxisLimits() }
    private val motionStatusRepository =
        mockk<MotionStatusRepository> { every { motionStatusFlow } returns motionStatusFlowMock }

    private lateinit var testSubject: ManualTurningUseCase

    private val mainThreadSurrogate = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        testSubject =
            ManualTurningUseCase(
                ioDispatcher = IoDispatcher(mainThreadSurrogate),
                cncStatusRepository = statusRepository,
                commandRepository = commandRepository,
                messagesRepository = messagesRepository,
                halRepository = halRepository,
                settingsRepository = settingsRepository,
                iniFileRepository = iniFileRepository,
                motionStatusRepository = motionStatusRepository
            )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `Test Something`() = runTest {
        joystickStatusFlow.emit(JoystickStatus(JoystickStatus.Position.XMinus, false))

        coVerify {
            messagesRepository.pushMessage(CncStateMessage.JoystickCannotFeedWithSpindleOff)
        }
    }
}
