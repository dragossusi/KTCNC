package com.mindovercnc.database.dao

import androidx.room.*
import com.mindovercnc.database.entity.CuttingInsertEntity

@Dao
abstract class CuttingInsertDao {
    @Query("SELECT * FROM cutting_insert") abstract suspend fun getAll(): List<CuttingInsertEntity>

    @Query("SELECT * FROM cutting_insert WHERE id = :id")
    abstract suspend fun getById(id: Int): CuttingInsertEntity?

    @Query("SELECT COUNT(id) FROM cutting_insert") abstract suspend fun getCount(): Long

    @Query("SELECT * FROM cutting_insert WHERE code = :code")
    abstract suspend fun getByCode(code: String): CuttingInsertEntity

    @Insert abstract suspend fun insertAll(entities: List<CuttingInsertEntity>)

    @Insert abstract suspend fun insert(entity: CuttingInsertEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: CuttingInsertEntity)

    @Delete abstract suspend fun delete(entity: CuttingInsertEntity)
}
