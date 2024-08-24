package com.mindovercnc.linuxcnc.screen.programs.picker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.screen.programs.picker.ProgramPickerState
import com.mindovercnc.linuxcnc.screen.programs.preview.ui.ProgramPreviewUi
import components.breadcrumb.BreadcrumbView
import components.filesystem.FileSystemView
import okio.Path

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ProgramsScreenUi(
    state: ProgramPickerState,
    onPathDropped: (Path) -> Unit,
    modifier: Modifier = Modifier
) {
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator()
    LaunchedEffect(state.preview) {
        scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
    }

    val breadCrumbView = remember {
        movableContentOf {
            BreadcrumbView(
                data = state.breadCrumbData,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                contentPadding = PaddingValues(horizontal = 8.dp))
        }
    }

    Column(modifier) {
        if (scaffoldNavigator.scaffoldDirective.maxHorizontalPartitions > 1) {
            breadCrumbView()
        }
        ListDetailPaneScaffold(
            directive = scaffoldNavigator.scaffoldDirective,
            value = scaffoldNavigator.scaffoldValue,
            modifier = Modifier.weight(1f),
            listPane = {
                // file explorer
                // val itemModifier = Modifier.fillMaxSize().weight(1f).widthIn(min = 120.dp)
                AnimatedPane(Modifier.widthIn(min = 120.dp)) {
                    if (scaffoldNavigator.scaffoldDirective.maxHorizontalPartitions == 1) {
                        breadCrumbView()
                    }

                    FileSystemView(data = state.fileSystemData, modifier = Modifier.fillMaxWidth())
                }
            },
            detailPane = {
                AnimatedPane(Modifier.widthIn(min = 120.dp)) {
                    // editor
                    // maybe use itemModifier
                    val editorModifier =
                        Modifier.programFileDropTarget { path -> onPathDropped(path) }
                    ProgramPreviewUi(state.preview, editorModifier)
                }
            })
    }
}

@Composable
internal expect fun Modifier.programFileDropTarget(onFileDropped: (Path) -> Unit): Modifier
