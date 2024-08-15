package com.mindovercnc.linuxcnc.screen.programs.preview

import com.mindovercnc.editor.impl.EditorLoaderImpl
import editor.EditorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okio.Path
import org.kodein.di.DI
import org.kodein.di.instance

class ProgramPreviewScreenModel(
    override val path: Path,
    di: DI,
) : ProgramPreviewComponent {

    private val editorLoader: EditorLoaderImpl by di.instance()

    private val mutableState =
        MutableStateFlow(ProgramPreviewState(editorState = createEditorState(path)))
    override val state = mutableState.asStateFlow()

    private fun createEditorState(path: Path): EditorState {
        val editor = editorLoader.loadEditor(path)
        return EditorState(editor)
    }
}
