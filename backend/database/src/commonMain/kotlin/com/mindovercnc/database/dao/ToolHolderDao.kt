package com.mindovercnc.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mindovercnc.database.entity.ToolHolderAndCutter
import com.mindovercnc.database.entity.ToolHolderEntity

@Dao
abstract class ToolHolderDao {
    @Query("SELECT * FROM tool_holder") abstract suspend fun getAll(): List<ToolHolderEntity>

    @Query("SELECT * FROM tool_holder")
    abstract suspend fun getAllWithCutter(): List<ToolHolderAndCutter>

    @Query("SELECT COUNT(id) FROM tool_holder") abstract suspend fun getCount(): Long

    @Insert abstract suspend fun insertAll(entities: List<ToolHolderEntity>)

    @Insert abstract suspend fun insert(entity: ToolHolderEntity)

    @Delete abstract suspend fun delete(entity: ToolHolderEntity)
}
