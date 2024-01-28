package com.mindovercnc.editor.impl

import com.mindovercnc.editor.Editor
import com.mindovercnc.editor.EditorLoader
import com.mindovercnc.editor.reader.EditorReader
import com.mindovercnc.editor.textlines.EmptyTextLines
import okio.Path

class EditorLoaderImpl(private val reader: EditorReader) : EditorLoader {

    override fun loadEditor(path: Path): Editor {
        return Editor(file = path) {
            try {
                with(reader) { path.readTextLines(this@Editor) }
            } catch (e: Throwable) {
                e.printStackTrace()
                EmptyTextLines
            }
        }
    }
}
