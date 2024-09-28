package com.mindovercnc.editor.reader

import com.mindovercnc.editor.IntList
import com.mindovercnc.editor.textlines.TextLineContent
import com.mindovercnc.editor.textlines.TextLines
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okio.FileSystem
import okio.Path
import okio.internal.commonToUtf8String

class PathEditorReader(private val fileSystem: FileSystem) : EditorReader {
    override fun Path.readTextLines(scope: CoroutineScope): TextLines {
        val buffer = fileSystem.read(this) { buffer.copy() }

        val lineStartPositions = IntList()

        val size = MutableStateFlow(0)

        val refreshJob =
            scope.launch {
                delay(100)
                size.value = lineStartPositions.size
                while (true) {
                    delay(1000)
                    size.value = lineStartPositions.size
                }
            }

        scope.launch {
            readLinePositions(lineStartPositions)
            refreshJob.cancel()
            size.value = lineStartPositions.size
        }

        return object : TextLines {

            override val size = size

            override fun get(index: Int): TextLineContent {
                val startPosition = lineStartPositions[index]
                val length =
                    if (index + 1 < size.value) lineStartPositions[index + 1] - startPosition
                    else buffer.size.toInt() - startPosition

                buffer.readUtf8Line()

                val bytearray = ByteArray(length)

                buffer.peek().read(sink = bytearray, byteCount = length, offset = startPosition)

                val text = bytearray.commonToUtf8String()
                return TextLineContent(index + 1, text)
            }
        }
    }

    private fun Path.readLinePositions(starts: IntList) {
        fileSystem.read(this) {
            val length = this.buffer.size
            require(length <= Int.MAX_VALUE) { "Files with size over ${Int.MAX_VALUE} aren't supported" }

            starts.update(capacity = length.toInt() / averageLineLength) {
                try {
                    var position = 0L
                    while (!exhausted()) {
                        val line = readUtf8Line()
                        if (line != null) {
                            starts.add(position.toInt())
                        }
                        position++
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    starts.clear(1)
                    starts.add(0)
                }
            }
        }
    }
}
