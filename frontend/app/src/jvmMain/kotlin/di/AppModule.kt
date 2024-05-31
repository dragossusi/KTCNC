package di

import AppConfig
import Communication
import Files
import StatusWatcher
import androidx.compose.runtime.Composable
import com.mindovercnc.data.lathehal.local.di.LatheHalLocalDataModule
import com.mindovercnc.data.lathehal.remote.di.LatheHalRemoteDataModule
import com.mindovercnc.data.linuxcnc.local.di.LinuxcncLegacyDataModule
import com.mindovercnc.data.linuxcnc.remote.di.LinuxcncRemoteDataModule
import com.mindovercnc.database.di.databaseModule
import com.mindovercnc.dispatchers.DispatchersModule
import com.mindovercnc.editor.reader.EditorReader
import com.mindovercnc.editor.reader.FileEditorReader
import com.mindovercnc.linuxcnc.di.*
import com.mindovercnc.linuxcnc.gcode.local.di.GCodeLocalModule
import com.mindovercnc.linuxcnc.settings.local.di.SettingsLocalModule
import com.mindovercnc.linuxcnc.settings.remote.di.SettingsRemoteModule
import com.mindovercnc.linuxcnc.tools.local.di.ToolsLocalModule
import com.mindovercnc.linuxcnc.tools.remote.di.ToolsRemoteModule
import kotlinx.datetime.Clock
import okio.FileSystem
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.compose.withDI
import org.kodein.di.instance

val BaseAppModule =
    DI.Module("base_app") {
        importAll(
            DispatchersModule,
            EditorModule,
            databaseModule(),
            InitializerModule,
            DomainModule,
        )
        bindSingleton { StatusWatcher(instance(), instance(), instance()) }

        // TODO change based on platform
        bindSingleton<EditorReader> { FileEditorReader }
    }

val SystemModule =
    DI.Module("system") {
        bindSingleton { FileSystem.SYSTEM }
        bindSingleton<Clock> { Clock.System }
    }

val LocalDataModule = DI.Module("local_data") {
    importAll(
        LinuxcncLegacyDataModule,
        LatheHalLocalDataModule,
        ToolsLocalModule,
        GCodeLocalModule,
        SettingsLocalModule
    )
}

val RemoteDataModule = DI.Module("remote_data") {
    importAll(
        LinuxcncRemoteDataModule,
        LatheHalRemoteDataModule,
        ToolsRemoteModule,
        SettingsRemoteModule,
        GrpcModule
    )
}

fun appDi(appConfig: AppConfig) = DI.Module("app") {
    val dataModule =
        when (appConfig.communication) {
            Communication.Local -> LocalDataModule
            is Communication.Remote -> RemoteDataModule
        }

    val iniModule = startupModule(appConfig.iniFile)

    importAll(
        KtLcncModule,
        iniModule,
        BaseAppModule,
        SystemModule,
        CommonDataModule,
        dataModule,
        ParseFactoryModule,
    )

    bindProvider(tag = "app_dir") { Files.appDir }
}

@Composable
fun withAppDi(
    appConfig: AppConfig,
    content: @Composable () -> Unit,
) {
    withDI(
        appDi(appConfig),
        content = content
    )
}
