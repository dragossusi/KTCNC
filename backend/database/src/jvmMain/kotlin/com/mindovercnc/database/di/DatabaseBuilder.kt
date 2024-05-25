package com.mindovercnc.database.di

import androidx.room.Room
import com.mindovercnc.database.KtcncDatabase
import java.io.File
import org.kodein.di.DI
import org.kodein.di.bindSingleton

actual fun DI.Builder.bindDatabaseBuilder() {
    bindSingleton {
        val dbFile = File(DB_NAME)
        Room.databaseBuilder<KtcncDatabase>(
            name = dbFile.absolutePath,
        )
    }
}
