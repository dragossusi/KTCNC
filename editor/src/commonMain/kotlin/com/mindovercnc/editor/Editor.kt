package com.mindovercnc.editor

import com.mindovercnc.editor.textlines.TextLines
import kotlinx.coroutines.CoroutineScope
import okio.Path

class Editor constructor(
    val file: Path,
    val lines: CoroutineScope.() -> TextLines,
)
