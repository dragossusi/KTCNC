package com.mindovercnc.database.dao

import androidx.room.*
import com.mindovercnc.database.entity.LatheToolEntity

@Dao
abstract class LatheToolDao {
    @Query("SELECT * FROM lathe_tool") abstract suspend fun getAll(): List<LatheToolEntity>

    @Query("SELECT * FROM lathe_tool WHERE id = :id")
    abstract suspend fun getById(id: Int): LatheToolEntity?

    @Query(
        """
        SELECT L.* FROM lathe_tool as L WHERE id NOT IN 
            (SELECT T.lathe_cutter_id FROM tool_holder as T WHERE T.lathe_cutter_id IS NOT NULL)
            """
    )
    abstract suspend fun getUnmountedTools(): List<LatheToolEntity>

    @Query("SELECT COUNT(id) FROM lathe_tool") abstract suspend fun getCount(): Long

    @Insert abstract suspend fun insertAll(entities: List<LatheToolEntity>)

    @Insert abstract suspend fun insert(entity: LatheToolEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun upsert(entity: LatheToolEntity)

    @Delete abstract suspend fun delete(entity: LatheToolEntity)

    @Query("DELETE FROM lathe_tool WHERE id = :id") abstract suspend fun deleteById(id: Int): Int
}
