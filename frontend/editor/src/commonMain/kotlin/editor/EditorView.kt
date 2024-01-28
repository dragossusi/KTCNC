package editor

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.mindovercnc.editor.textlines.TextLineContent
import com.mindovercnc.editor.textlines.TextLines
import com.mindovercnc.editor.type.EditorFileType
import com.mindovercnc.editor.type.EditorFileTypeHandler
import editor.line.LineNumber
import editor.util.loadableScoped
import editor.util.withoutWidthConstraints
import okio.Path
import org.kodein.di.compose.rememberInstance
import scroll.VerticalScrollbar
import scroll.draggableScroll
import kotlin.text.Regex.Companion.fromLiteral

@Composable
fun EditorView(
    state: EditorState,
    showFileName: Boolean = true,
    modifier: Modifier = Modifier
) {
    val model = state.editor
    key(model) {
        val editorTheme = LocalEditorTheme.current
        Surface(
            modifier = modifier,
            color = editorTheme.background.toColor(),
        ) {
            val (lines) = loadableScoped(model.lines)

            if (lines != null) {
                TypedEditor(model.file) {
                    Lines(
                        file = model.file,
                        lines = lines,
                        showFileName = showFileName,
                        settings = state.settings,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {
                CircularProgressIndicator(modifier = Modifier.size(36.dp).padding(4.dp))
            }
        }
    }
}

@Composable
private fun TypedEditor(path: Path, content: @Composable () -> Unit) {
    val editorTypeHandle: EditorFileTypeHandler by rememberInstance()
    val type = remember { editorTypeHandle.getFileType(path) }

    CompositionLocalProvider(LocalEditorFileType provides type, content = content)
}

@Composable
private fun rememberDigitCount(size: Int): String {
    return remember(size) {
        val count = size.toString().length
        (1..count).joinToString(separator = "") { "9" }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Lines(
    file: Path,
    lines: TextLines,
    showFileName: Boolean,
    settings: EditorSettings,
    modifier: Modifier = Modifier
) {
    val size by lines.size.collectAsState()
    val maxNum = rememberDigitCount(size)

    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {
        val scrollState = rememberLazyListState()
        val lineHeight = with(LocalDensity.current) { settings.fontSize.toDp() * 2f }

        LazyColumn(
            modifier = Modifier.fillMaxSize().draggableScroll(scrollState, scope),
            state = scrollState
        ) {
            if (showFileName) {
                stickyHeader {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        FileNameHeader(file, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
            items(size) { index ->
                Box(modifier = Modifier.height(lineHeight)) {
                    Line(
                        maxNum = maxNum,
                        line = lines[index],
                        settings = settings,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }
            }
        }

        VerticalScrollbar(Modifier.align(Alignment.CenterEnd).width(20.dp), scrollState)
    }
}

@Composable
private fun Line(
    maxNum: String,
    line: TextLineContent,
    settings: EditorSettings,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        DisableSelection {
            val background = LocalEditorTheme.current.lineNumber.background.toColor()
            Box(modifier = Modifier.background(background)) {
                LineNumber(
                    number = maxNum,
                    settings = settings,
                    modifier = Modifier.alpha(0f).padding(start = 12.dp)
                )
                LineNumber(
                    number = line.number.toString(),
                    settings = settings,
                    modifier = Modifier.align(Alignment.CenterEnd).padding(start = 12.dp)
                )
            }
            LineContent(
                line = line,
                modifier =
                Modifier.weight(1f)
                    .withoutWidthConstraints()
                    .padding(start = 28.dp, end = 12.dp),
                settings = settings
            )
        }
    }
}

@Composable
private fun LineContent(
    line: TextLineContent,
    settings: EditorSettings,
    modifier: Modifier = Modifier
) {
    Text(
        text =
        when (LocalEditorFileType.current) {
            EditorFileType.GCODE -> codeString(line.text)
            EditorFileType.NORMAL -> normalString(line.text)
        },
        fontSize = settings.fontSize,
        fontFamily = FontFamily.Monospace,
        modifier = modifier,
        softWrap = false
    )
}

@Composable
private fun normalString(str: String): AnnotatedString {
    val editorTheme = LocalEditorTheme.current
    val style = editorTheme.text.toSpanStyle()
    return buildAnnotatedString { withStyle(style) { append(str) } }
}

@Composable
private fun codeString(str: String): AnnotatedString {
    val editorTheme = LocalEditorTheme.current

    val keyword = editorTheme.keyword.toSpanStyle()
    val punctuation = editorTheme.punctuation.toSpanStyle()
    val value = editorTheme.value.toSpanStyle()
    val comment = editorTheme.comment.toSpanStyle()
    val variable = editorTheme.variable.toSpanStyle()
    val gcode = editorTheme.gcode.toSpanStyle()

    val strFormatted = str.replace("\t", "    ")

    return buildAnnotatedString {
        withStyle(editorTheme.text.toSpanStyle()) {
            append(strFormatted)
            for (item in EditorConstants.punctuation) {
                addStyle(punctuation, strFormatted, item)
            }

            // gcode
            addGcodeStyle(gcode, strFormatted)

            // variable
            addStyle(variable, strFormatted, Regex("[#oO]<(.+)>"))

            // keywords
            addKeywords(keyword, strFormatted)

            // value
            addStyle(value, strFormatted, "true")
            addStyle(value, strFormatted, "false")

            // number
            addStyle(value, strFormatted, Regex("(^|\\s+)(-?\\d+.?\\d*)(?=\\s+|\$)"))

            // addStyle(AppTheme.code.value, strFormatted, Regex("[0-9]*"))
            // addStyle(AppTheme.code.annotation, strFormatted, Regex("^@[a-zA-Z_]*"))
            addStyle(comment, strFormatted, Regex("^\\s*; .*"))
            addStyle(comment, strFormatted, Regex("\\((.*)\\)"))
        }
    }
}

private fun AnnotatedString.Builder.addKeywords(style: SpanStyle, text: String) {
    val words = EditorConstants.keywords

    words.forEach {
        addStyle(style, text, Regex("(^|\\s+)($it)(?=\\s+|\$)"))
        addStyle(style, text, Regex("(^|\\s+)(${it.uppercase()})(?=\\s+|\$)"))
    }
}

private fun AnnotatedString.Builder.addGcodeStyle(style: SpanStyle, text: String) {
    val letters = EditorConstants.gcodeCharacters
    val letterString =
        letters.joinToString(separator = "", prefix = "[", postfix = "]") {
            "$it${it.lowercaseChar()}"
        }
    addStyle(style, text, Regex("(^|\\s+)($letterString)(-?\\d+\\.?\\d*)(?=\\s+|\$)"))
}

private fun AnnotatedString.Builder.addStyle(style: SpanStyle, text: String, regexp: String) {
    addStyle(style, text, fromLiteral(regexp))
}

private fun AnnotatedString.Builder.addStyle(style: SpanStyle, text: String, regexp: Regex) {
    for (result in regexp.findAll(text)) {
        addStyle(style, result.range.first, result.range.last + 1)
    }
}
