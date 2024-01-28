package com.mindovercnc.editor.di

import com.mindovercnc.editor.EditorLoader
import com.mindovercnc.editor.EditorThemeLoader
import com.mindovercnc.editor.impl.EditorLoaderImpl
import com.mindovercnc.editor.impl.EditorThemeLoaderImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val EditorThemeModule = DI.Module("editor_theme") {
    bindProvider<EditorThemeLoader> { EditorThemeLoaderImpl(instance(), instance()) }
    bindProvider<EditorLoader> { EditorLoaderImpl(instance()) }
}