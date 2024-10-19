package com.mindovercnc.database.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mindovercnc.database.KtcncDatabase
import com.mindovercnc.database.initializer.DummyToolsInitializer
import initializer.Initializer
import initializer.SimpleInitializer
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

internal const val DB_NAME = "LatheTools.db"

fun databaseModule() =
    DI.Module("db") {
        bindDatabaseBuilder()
        bindSingleton {
            val builder = instance<RoomDatabase.Builder<KtcncDatabase>>()
            builder
                .setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
                .build()
        }
        bindSingleton<Initializer>("database") {
            SimpleInitializer(
                //            ConnectionInitializer(path.div(DB_NAME)),
                //            SchemaInitializer(true),
                DummyToolsInitializer(instance(), 5)
            )
        }
        bindProvider { instance<KtcncDatabase>().cuttingInsertDao }
        bindProvider { instance<KtcncDatabase>().latheToolDao }
        bindProvider { instance<KtcncDatabase>().toolHolderDao }
        bindProvider { instance<KtcncDatabase>().workPieceMaterialDao }
    }
