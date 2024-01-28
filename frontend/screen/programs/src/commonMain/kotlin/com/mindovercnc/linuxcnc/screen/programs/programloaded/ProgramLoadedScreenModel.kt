package com.mindovercnc.linuxcnc.screen.programs.programloaded

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.data.linuxcnc.IniFileRepository
import com.mindovercnc.dispatchers.IoDispatcher
import com.mindovercnc.editor.impl.EditorLoaderImpl
import com.mindovercnc.linuxcnc.actor.PathActor
import com.mindovercnc.linuxcnc.actor.ProgramData
import com.mindovercnc.linuxcnc.domain.*
import com.mindovercnc.linuxcnc.domain.model.ActiveCode
import com.mindovercnc.linuxcnc.domain.model.PathUiState
import com.mindovercnc.linuxcnc.domain.model.ZoomRange
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ui.ToolChangeModel
import com.mindovercnc.model.MachineLimits
import editor.EditorState
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.Path
import org.jetbrains.skia.Point
import org.kodein.di.DI
import org.kodein.di.instance
import kotlin.math.min

class ProgramLoadedScreenModel(di: DI, componentContext: ComponentContext) :
    BaseScreenModel<ProgramLoadedState>(ProgramLoadedState(), componentContext),
    ProgramLoadedComponent {

    private val file: Path by di.instance()
    private val gCodeUseCase: GCodeUseCase by di.instance()
    private val offsetsUseCase: OffsetsUseCase by di.instance()
    private val positionUseCase: PositionUseCase by di.instance()
    private val activeCodesUseCase: ActiveCodesUseCase by di.instance()
    private val programsUseCase: ProgramsUseCase by di.instance()
    private val editorLoader: EditorLoaderImpl by di.instance()
    private val spindleUseCase: SpindleUseCase by di.instance()
    private val feedUseCase: FeedUseCase by di.instance()
    private val iniFileRepository: IniFileRepository by di.instance()
    private val manualToolChangeUseCase: ManualToolChangeUseCase by di.instance()
    private val ioDispatcher: IoDispatcher by di.instance()

    // how much free space to have around the drawing
    private val viewportPadding = 70 // px
    private val toolTrace = mutableListOf<Point>()

    init {
        coroutineScope.launch(ioDispatcher.dispatcher) {
            val editor = editorLoader.loadEditor(file)
            mutableState.update { it.copy(editorState = EditorState(editor)) }
        }

        // programsUseCase.loadProgram(file)
        val machineLimits =
            with(iniFileRepository.getActiveLimits()) {
                MachineLimits(
                    xMin = xMinLimit!!,
                    xMax = xMaxLimit!!,
                    zMin = zMinLimit!!,
                    zMax = zMaxLimit!!
                )
            }
        mutableState.update {
            it.copy(visualTurningState = it.visualTurningState.copy(machineLimits = machineLimits))
        }

        offsetsUseCase.currentOffset
            .filterNotNull()
            .onEach { wcs ->
                mutableState.update {
                    it.copy(
                        currentWcs = wcs.coordinateSystem,
                        visualTurningState = it.visualTurningState.copyWithWcs(wcs)
                    )
                }
            }
            .launchIn(coroutineScope)

        coroutineScope.launch {
            val pathElements = gCodeUseCase.getPathElements(file)
            val initialPathActor = PathActor(pathElements)
            val defaultPixelsPerUnit =
                calculateDefaultPxPerUnit(
                    viewportSize = mutableState.value.visualTurningState.viewportSize,
                    programSize = initialPathActor.programSize,
                )
            val pathActor = initialPathActor.rescaled(defaultPixelsPerUnit)
            val pathUiState = PathUiState(pathActor)
            mutableState.update {
                val rulers = it.visualTurningState.programRulers.rescaled(defaultPixelsPerUnit)
                it.copy(
                    visualTurningState =
                    it.visualTurningState.copy(
                        pathUiState = pathUiState,
                        programRulers = rulers,
                        defaultPixelsPerUnit = defaultPixelsPerUnit,
                        translate =
                        pathUiState.getInitialTranslate(
                            viewportSize = it.visualTurningState.viewportSize
                        )
                    )
                )
            }
        }

        positionUseCase
            .getToolPosition()
            .onEach { point ->
                toolTrace.add(point)
                mutableState.update { currentState ->
                    currentState.copy(
                        visualTurningState =
                        currentState.visualTurningState.copy(toolPosition = point)
                    )
                }
            }
            .launchIn(coroutineScope)

        activeCodesUseCase
            .getActiveCodes()
            .onEach { codes -> mutableState.update { it.copy(activeCodes = codes) } }
            .launchIn(coroutineScope)

        programsUseCase.uiModel
            .onEach { model -> mutableState.update { it.copy(positionModel = model) } }
            .launchIn(coroutineScope)

        spindleUseCase
            .spindleFlow()
            .onEach { model ->
                mutableState.update {
                    it.copy(
                        machineStatus =
                        it.machineStatus.copy(
                            spindleOverride = model.spindleOverride,
                            actualSpindleSpeed = model.actualRpm
                        )
                    )
                }
            }
            .launchIn(coroutineScope)

        feedUseCase
            .feedFlow()
            .onEach { model ->
                mutableState.update {
                    it.copy(
                        machineStatus =
                        it.machineStatus.copy(
                            feedOverride = model.feedOverride,
                        )
                    )
                }
            }
            .launchIn(coroutineScope)

        manualToolChangeUseCase.toolToChange
            .onEach { toolNo ->
                println("---update state: $toolNo")
                mutableState.update {
                    val toolChangeModel = when {
                        toolNo != null -> ToolChangeModel(toolNo)
                        else -> null
                    }
                    it.copy(toolChangeModel = toolChangeModel)
                }
            }
            .launchIn(coroutineScope)
    }

    override fun zoomOut() = setNewScale { it - 0.25f }

    override fun zoomIn() = setNewScale { it + 0.25f }

    override fun zoomBy(factor: Float) = setNewScale { it * factor }

    override fun center() {
        mutableState.update { currentState ->
            currentState.copy(
                visualTurningState =
                currentState.visualTurningState.copy(
                    translate =
                    currentState.visualTurningState.pathUiState.getInitialTranslate(
                        viewportSize = currentState.visualTurningState.viewportSize
                    )
                )
            )
        }
    }

    override fun translate(offset: Offset) {
        mutableState.update {
            it.copy(
                visualTurningState =
                it.visualTurningState.copy(
                    translate = it.visualTurningState.translate.plus(offset)
                )
            )
        }
        // println("Translate: ${mutableState.value.visualTurningState.translate}")
    }

    override fun setViewportSize(size: IntSize) {
        mutableState.update {
            it.copy(visualTurningState = it.visualTurningState.copy(viewportSize = size))
        }
    }

    override fun runProgram() {
        programsUseCase.runProgram()
    }

    override fun stopProgram() {
        programsUseCase.stopProgram()
    }

    override fun confirmToolChanged() {
        coroutineScope.launch { manualToolChangeUseCase.confirmToolChange() }
    }

    override fun cancelToolChange() {
        manualToolChangeUseCase.cancelToolChange()
    }

    override fun onActiveCodeClicked(activeCode: ActiveCode) {
        activeCodesUseCase.getCodeDescription(activeCode)
    }

    private fun setNewScale(block: (Float) -> Float) {
        mutableState.update { currentState ->
            val newScale = block(currentState.visualTurningState.scale)

            if (newScale !in ZoomRange) {
                // don't update if value outside of range
                return@update currentState
            }

            val scaleDelta = newScale - currentState.visualTurningState.scale
            val pixelPerUnit = currentState.visualTurningState.defaultPixelsPerUnit * newScale
            val pathUiState = currentState.visualTurningState.pathUiState.rescaled(pixelPerUnit)
            val rulers = currentState.visualTurningState.programRulers.rescaled(pixelPerUnit)
            currentState.copy(
                visualTurningState =
                currentState.visualTurningState.copy(
                    scale = newScale,
                    pathUiState = pathUiState,
                    programRulers = rulers,
                )
            )
        }
    }

    private fun calculateDefaultPxPerUnit(
        viewportSize: IntSize,
        programSize: ProgramData.ProgramSize,
    ): Float {
        val drawableWidth = viewportSize.width - viewportPadding
        val drawableHeight = viewportSize.height - viewportPadding
        val widthRatio = drawableWidth.div(programSize.width)
        val heightRatio = drawableHeight.div(programSize.height)
        return min(widthRatio, heightRatio)
    }
}
