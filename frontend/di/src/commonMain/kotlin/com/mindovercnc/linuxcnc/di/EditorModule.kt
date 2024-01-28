package com.mindovercnc.linuxcnc.di

import com.mindovercnc.editor.impl.EditorLoaderImpl
import com.mindovercnc.editor.type.EditorFileTypeHandler
import com.mindovercnc.editor.type.EditorFileTypeHandlerImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val EditorModule = DI.Module("editor") {
    bindSingleton { EditorLoaderImpl(instance()) }

    //    bindSingleton<EditorReader> { PathEditorReader(instance()) }
    bindSingleton<EditorFileTypeHandler> { EditorFileTypeHandlerImpl }
}
