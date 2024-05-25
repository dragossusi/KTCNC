package com.mindovercnc.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mindovercnc.database.entity.LatheToolEntity

@Dao
abstract class LatheToolDao {
    @Query("SELECT * FROM lathe_tool") abstract suspend fun getAll(): List<LatheToolEntity>

    @Query("SELECT * FROM lathe_tool WHERE id = :id")
    abstract suspend fun getById(id: Int): LatheToolEntity?

    @Query("SELECT COUNT(id) FROM lathe_tool") abstract suspend fun getCount(): Long

    @Insert abstract suspend fun insertAll(entities: List<LatheToolEntity>)

    @Insert abstract suspend fun insert(entity: LatheToolEntity)

    @Delete abstract suspend fun delete(entity: LatheToolEntity)
}
