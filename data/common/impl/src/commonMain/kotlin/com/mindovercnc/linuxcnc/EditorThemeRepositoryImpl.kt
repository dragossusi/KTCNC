package com.mindovercnc.linuxcnc

import com.mindovercnc.editor.EditorTheme
import com.mindovercnc.editor.EditorThemeLoader
import com.mindovercnc.repository.EditorThemeRepository
import okio.Path

/** Implementation for [EditorThemeRepository]. */
class EditorThemeRepositoryImpl(
    appDir: Path,
    private val editorThemeLoader: EditorThemeLoader
) : EditorThemeRepository {

    private val editorDir = appDir.div("editor")

    override suspend fun getEditorTheme(): EditorTheme? {
        return editorThemeLoader.load(editorDir.div("theme"))
    }
}
