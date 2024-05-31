package com.mindovercnc.database.dao

import androidx.room.*
import com.mindovercnc.database.entity.ToolHolderAndCutter
import com.mindovercnc.database.entity.ToolHolderEntity

@Dao
abstract class ToolHolderDao {
    @Query("SELECT * FROM tool_holder") abstract suspend fun getAll(): List<ToolHolderEntity>

    @Query("SELECT * FROM tool_holder")
    abstract suspend fun getAllWithCutter(): List<ToolHolderAndCutter>

    @Query("SELECT COUNT(holder_number) FROM tool_holder") abstract suspend fun getCount(): Long

    @Insert abstract suspend fun insertAll(entities: List<ToolHolderEntity>)

    @Insert abstract suspend fun insert(entity: ToolHolderEntity)
    @Update
    abstract suspend fun update(entity: ToolHolderEntity)

    @Delete abstract suspend fun delete(entity: ToolHolderEntity)

    @Query("DELETE FROM tool_holder WHERE holder_number = :holderNumber")
    abstract suspend fun deleteByNumber(holderNumber: Int): Int
}
