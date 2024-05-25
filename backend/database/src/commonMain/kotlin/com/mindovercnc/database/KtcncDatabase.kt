package com.mindovercnc.database

import androidx.room.Database
import androidx.room.RoomDatabase
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
abstract class KtcncDatabase : RoomDatabase() {
    abstract val cuttingInsertDao: CuttingInsertDao
    abstract val latheToolDao: LatheToolDao
    abstract val toolHolderDao: ToolHolderDao
    abstract val workPieceMaterialDao: WorkPieceMaterialDao
}
