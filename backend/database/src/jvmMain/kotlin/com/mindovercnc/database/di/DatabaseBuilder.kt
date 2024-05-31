package com.mindovercnc.database.di

import androidx.room.Room
import com.mindovercnc.database.KtcncDatabase
import okio.Path
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

actual fun DI.Builder.bindDatabaseBuilder() {
    bindSingleton {
        val appDir: Path = instance("app_dir")
        val dbFile = appDir.div(DB_NAME).toFile()
        Room.databaseBuilder<KtcncDatabase>(
            name = dbFile.absolutePath,
        )
    }
}
