package com.mindovercnc.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mindovercnc.database.entity.WorkpieceMaterialEntity

@Dao
abstract class WorkPieceMaterialDao {
    @Query("SELECT * FROM workpiece_material")
    abstract suspend fun getAll(): List<WorkpieceMaterialEntity>

    @Query("SELECT COUNT(id) FROM workpiece_material") abstract suspend fun getCount(): Long

    @Insert abstract suspend fun insertAll(entities: List<WorkpieceMaterialEntity>)

    @Insert abstract suspend fun insert(entity: WorkpieceMaterialEntity)

    @Delete abstract suspend fun delete(entity: WorkpieceMaterialEntity)
}
