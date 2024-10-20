package com.mindovercnc.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.mindovercnc.database.dao.CuttingInsertDao
import com.mindovercnc.database.dao.LatheToolDao
import com.mindovercnc.database.dao.ToolHolderDao
import com.mindovercnc.database.dao.WorkPieceMaterialDao
import com.mindovercnc.database.entity.*

@Database(
    entities = [
        CuttingInsertEntity::class,
        FeedsAndSpeedsEntity::class,
        LatheToolEntity::class,
        ToolHolderEntity::class,
        WorkpieceMaterialEntity::class
    ],
    version = 1
)
@ConstructedBy(KtcncDatabaseConstructor::class)
abstract class KtcncDatabase : RoomDatabase() {
    abstract val cuttingInsertDao: CuttingInsertDao
    abstract val latheToolDao: LatheToolDao
    abstract val toolHolderDao: ToolHolderDao
    abstract val workPieceMaterialDao: WorkPieceMaterialDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object KtcncDatabaseConstructor : RoomDatabaseConstructor<KtcncDatabase> {
    override fun initialize(): KtcncDatabase
}