package com.mindovercnc.linuxcnc.tools.local

import com.mindovercnc.database.dao.CuttingInsertDao
import com.mindovercnc.database.entity.CuttingInsertEntity
import com.mindovercnc.linuxcnc.tools.CuttingInsertsRepository
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert

/** Implementation for [CuttingInsertsRepository]. */
class CuttingInsertsRepositoryLocal(
    private val cuttingInsertDao: CuttingInsertDao,
) : CuttingInsertsRepository {

    override suspend fun insert(cuttingInsert: CuttingInsert) {
        val entity = cuttingInsert.toEntity()
        cuttingInsertDao.insert(entity)
    }

    override suspend fun update(cuttingInsert: CuttingInsert) {
        val entity = cuttingInsert.toEntity()
        cuttingInsertDao.update(entity)
    }

    override suspend fun findAll(): List<CuttingInsert> {
        return cuttingInsertDao.getAll().map { it.toCuttingInsert() }
    }

    override suspend fun delete(cuttingInsert: CuttingInsert) {
        return cuttingInsertDao.delete(cuttingInsert.toEntity())
    }

    private fun CuttingInsert.toEntity() = CuttingInsertEntity(
        madeOf = madeOf,
        code = code,
        tipRadius = tipRadius,
        tipAngle = tipAngle,
        size = size,
    )
}
