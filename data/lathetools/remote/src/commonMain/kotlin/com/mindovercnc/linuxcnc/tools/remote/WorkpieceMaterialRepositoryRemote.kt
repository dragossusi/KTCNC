package com.mindovercnc.linuxcnc.tools.remote

import com.mindovercnc.linuxcnc.tools.WorkpieceMaterialRepository
import com.mindovercnc.linuxcnc.tools.model.WorkpieceMaterial
import mu.KotlinLogging


/** Implementation for [WorkpieceMaterialRepository]. */
class WorkpieceMaterialRepositoryRemote : WorkpieceMaterialRepository {


    init {
        LOG.warn { "Remote implementation is missing" }
    }

    override suspend fun insert(wpMaterial: WorkpieceMaterial) {
        /* no-op */
    }

    override suspend fun findAll(): List<WorkpieceMaterial> {
        /* no-op */
        return emptyList()
    }

    companion object {
        private val LOG = KotlinLogging.logger("WorkpieceMaterialRepository")
    }
}
