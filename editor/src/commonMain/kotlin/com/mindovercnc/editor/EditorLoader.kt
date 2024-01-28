package com.mindovercnc.editor

import okio.Path

/** Loads an editor from a [Path]. */
interface EditorLoader {
    fun loadEditor(path: Path): Editor
}