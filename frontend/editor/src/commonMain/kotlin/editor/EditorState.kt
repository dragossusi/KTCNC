package editor

import com.mindovercnc.editor.Editor

data class EditorState(
    val editor: Editor,
    val settings: EditorSettings = EditorSettings()
)