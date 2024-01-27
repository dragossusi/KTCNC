package di

import StatusWatcher
import androidx.compose.runtime.Composable
import com.mindovercnc.data.linuxcnc.remote.di.LinuxcncRemoteDataModule
import com.mindovercnc.dispatchers.DispatchersModule
import com.mindovercnc.linuxcnc.di.*
import com.mindovercnc.linuxcnc.tools.remote.di.ToolsRemoteModule
import kotlinx.datetime.Clock
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.compose.withDI
import org.kodein.di.instance

val BaseAppModule =
    DI.Module("AppModule") {
        importAll(
            DispatchersModule,
            EditorModule,
            DomainModule,
        )
        bindSingleton { StatusWatcher(instance(), instance(), instance()) }

        bindSingleton<Clock> { Clock.System }

        //    bindSingleton<FileSystem> { NodeJsFileSystem }
        // TODO change based on platform
        //    bindSingleton<EditorReader> { PathEditorReader(instance()) }
    }

val RepositoryModule =
    DI.Module("repository") {
        importAll(CommonDataModule, LinuxcncRemoteDataModule, ToolsRemoteModule, GrpcModule)
    }

@Composable
fun withAppDi(content: @Composable () -> Unit) =
    withDI(BaseAppModule, RepositoryModule, content = content)
