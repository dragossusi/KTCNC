package com.mindovercnc.editor

import okio.Path

interface EditorThemeLoader {
    suspend fun load(file: Path): EditorTheme?
    suspend fun export(file: Path, theme: EditorTheme)
}