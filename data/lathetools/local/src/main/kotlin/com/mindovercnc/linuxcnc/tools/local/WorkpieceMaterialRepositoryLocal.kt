package com.mindovercnc.linuxcnc.tools.local

import com.mindovercnc.database.dao.WorkPieceMaterialDao
import com.mindovercnc.database.entity.WorkpieceMaterialEntity
import com.mindovercnc.linuxcnc.tools.WorkpieceMaterialRepository
import com.mindovercnc.linuxcnc.tools.model.WorkpieceMaterial


/** Implementation for [WorkpieceMaterialRepository]. */
class WorkpieceMaterialRepositoryLocal(
    private val workPieceMaterialDao: WorkPieceMaterialDao
) : WorkpieceMaterialRepository {

    override suspend fun insert(wpMaterial: WorkpieceMaterial) {
        val entity = WorkpieceMaterialEntity(name = wpMaterial.name, category = "")
        workPieceMaterialDao.insert(entity)
    }

    override suspend fun findAll(): List<WorkpieceMaterial> {
        return workPieceMaterialDao.getAll().map { it.toWorkpieceMaterial() }
    }

}
