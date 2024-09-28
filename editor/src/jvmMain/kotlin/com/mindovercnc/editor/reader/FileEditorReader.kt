package com.mindovercnc.editor.reader

import com.mindovercnc.editor.IntList
import com.mindovercnc.editor.textlines.TextLineContent
import com.mindovercnc.editor.textlines.TextLines
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okio.Path
import java.io.FileInputStream
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.channels.FileChannel
import java.nio.charset.StandardCharsets

/**
 * Implementation for [EditorReader] using [java.io.File].
 *
 * Will be replaced by [PathEditorReader].
 */
@Deprecated(
    "This does not support multiplatform.",
    replaceWith = ReplaceWith("PathEditorReader", "com.mindovercnc.editor.reader.PathEditorReader"),
    level = DeprecationLevel.WARNING
)
object FileEditorReader : EditorReader {

    override fun Path.readTextLines(scope: CoroutineScope): TextLines {
        var byteBufferSize: Int
        val byteBuffer =
            RandomAccessFile(this.toFile(), "r").use { file ->
                byteBufferSize = file.length().toInt()
                file.channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length())
            }

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

        scope.launch(Dispatchers.IO) {
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
                    else byteBufferSize - startPosition
                // Only JDK since 13 has slice() method we need, so do ugly for now.
                byteBuffer.position(startPosition)
                val slice = byteBuffer.slice()
                slice.limit(length)
                return TextLineContent(index + 1, StandardCharsets.UTF_8.decode(slice).toString())
            }
        }
    }

    private fun Path.readLinePositions(starts: IntList) {
        val file = toFile()
        require(file.length() <= Int.MAX_VALUE) {
            "Files with size over ${Int.MAX_VALUE} aren't supported"
        }

        starts.update(capacity = file.length().toInt() / averageLineLength) {
            try {
                FileInputStream(file).use {
                    val channel = it.channel
                    val ib = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size())
                    var isBeginOfLine = true
                    var position = 0L
                    while (ib.hasRemaining()) {
                        val byte = ib.get()
                        if (isBeginOfLine) {
                            starts.add(position.toInt())
                        }
                        isBeginOfLine = byte.toInt().toChar() == '\n'
                        position++
                    }
                    channel.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                starts.clear(1)
                starts.add(0)
            }
        }
    }
}
