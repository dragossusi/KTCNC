package com.mindovercnc.linuxcnc.screen.programs.picker

import com.arkivanov.decompose.ComponentContext
import com.mindovercnc.data.linuxcnc.FileSystemRepository
import com.mindovercnc.linuxcnc.domain.BreadCrumbDataUseCase
import com.mindovercnc.linuxcnc.domain.FileSystemDataUseCase
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.linuxcnc.screen.programs.preview.ProgramPreviewComponent
import com.mindovercnc.linuxcnc.screen.programs.preview.ProgramPreviewScreenModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mu.KotlinLogging
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import org.kodein.di.DI
import org.kodein.di.instance
import ro.dragossusi.ktcnc.rpc.FileResponse

class ProgramPickerScreenModel(private val di: DI, componentContext: ComponentContext) :
    BaseScreenModel<ProgramPickerState>(ProgramPickerState(), componentContext),
    ProgramPickerComponent {

    private val fileSystemRepository: FileSystemRepository by di.instance()
    private val fileSystem: FileSystem by di.instance()
    private val fileSystemDataUseCase: FileSystemDataUseCase by di.instance()
    private val breadCrumbDataUseCase: BreadCrumbDataUseCase by di.instance()

    private val logger = KotlinLogging.logger("ProgramsRootScreenModel")

    init {
        coroutineScope.launch {
            val file = fileSystemRepository.getNcRootAppFile()
            logger.info { "NC Root App File path ${file.path}" }
            setCurrentFolder(file)
        }
    }

    override fun showError(error: String) {
        mutableState.update { it.copy(error = error) }
    }

    override fun clearError() {
        mutableState.update { it.copy(error = null) }
    }

    override fun selectItem(file: FileResponse) {
        if (file.isDirectory) {
            println("---Folder clicked: ${file.path}")
            loadFolderContents(file)
        } else {
            setCurrentFile(file.path.toPath())
        }
    }

    override fun loadFolderContents(file: FileResponse) {
        setCurrentFolder(file)
        setCurrentFile(null)
    }

    override fun setCurrentFile(file: Path?) {
        logger.info { "Setting current file to $file" }
        mutableState.update {
            it.copy(preview = file?.let(::createPreviewComponent))
        }
    }

    private fun loadFolder(path: Path) {
        coroutineScope.launch {
            val file = fileSystemRepository.getFile(path.toString())
            setCurrentFolder(file)
        }
    }

    private fun setCurrentFolder(file: FileResponse) {
        logger.info { "Setting current folder to $file" }
        coroutineScope.launch {
            val fileSystemData =
                with(fileSystemDataUseCase) { file.toFileSystemData(onItemClick = ::selectItem) }
            val breadCrumbData =
                with(breadCrumbDataUseCase) {
                    file.toBreadCrumbData(onItemClick = ::loadFolder)
                }
            logger.info { breadCrumbData.items.joinToString { it.title } }

            mutableState.update {
                it.copy(breadCrumbData = breadCrumbData, fileSystemData = fileSystemData)
            }
        }
    }

    private fun createPreviewComponent(path: Path): ProgramPreviewComponent {
        return ProgramPreviewScreenModel(path, di)
    }
}
