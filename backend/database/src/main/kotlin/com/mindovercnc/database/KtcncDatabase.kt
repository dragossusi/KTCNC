package com.mindovercnc.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mindovercnc.database.entity.CuttingInsertEntity

@Database(entities = [CuttingInsertEntity::class], version = 1)
abstract class KtcncDatabase : RoomDatabase() {}
