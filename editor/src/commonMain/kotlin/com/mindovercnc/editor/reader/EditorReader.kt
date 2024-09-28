package com.mindovercnc.editor.reader

import com.mindovercnc.editor.textlines.TextLines
import kotlinx.coroutines.CoroutineScope
import okio.Path

interface EditorReader {

    /** Reads [TextLines] from a [Path]. */
    fun Path.readTextLines(scope: CoroutineScope): TextLines
}

internal const val averageLineLength = 200
