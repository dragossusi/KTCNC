package di

import com.mindovercnc.linuxcnc.initializer.KtlCncInitializer
import initializer.Initializer
import initializer.SimpleInitializer
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import startup.AppDirInitializer
import startup.StatusWatchInitializer

val InitializerModule = DI.Module("initializer") {
    bindSingleton("app") {
        val appDirInitializer: AppDirInitializer = instance()
        val ktlCncInitializer: KtlCncInitializer = instance()
        val databaseInitializer: Initializer = instance("database")
        val statusWatchInitializer: StatusWatchInitializer = instance()

        SimpleInitializer(appDirInitializer, ktlCncInitializer, databaseInitializer, statusWatchInitializer)
    }

    bindSingleton { AppDirInitializer(instance()) }

    bindSingleton { KtlCncInitializer(instance("app_dir")) }
    bindSingleton { StatusWatchInitializer(instance(), instance()) }
}