package com.mindovercnc.linuxcnc.tools.local

import com.mindovercnc.database.dao.CuttingInsertDao
import com.mindovercnc.database.dao.LatheToolDao
import com.mindovercnc.database.entity.LatheToolEntity
import com.mindovercnc.linuxcnc.tools.LatheToolRepository
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolType
import com.mindovercnc.model.TipOrientation
import mu.KotlinLogging

/** Implementation for [LatheToolRepository]. */
class LatheToolRepositoryLocal(
    private val latheToolDao: LatheToolDao,
    private val cuttingInsertDao: CuttingInsertDao
) : LatheToolRepository {

    override suspend fun getLatheTools(): List<LatheTool> {
        return latheToolDao.getAll().also {
            LOG.info { "Found ${it.size} lathe tools" }
        }.mapNotNull { it.toLatheTool() }
    }

    override suspend fun getUnmountedLatheTools(): List<LatheTool> {
        return latheToolDao.getUnmountedTools().mapNotNull { it.toLatheTool() }
    }

    override suspend fun createLatheTool(latheTool: LatheTool) {
        val entity =
            when (latheTool) {
                is LatheTool.Turning ->
                    LatheToolEntity(
                        insertId = latheTool.insert.id,
                        type = ToolType.Turning,
                        tipOrientation = latheTool.tipOrientation.orient,
                        spindleDirection = latheTool.spindleDirection,
                    )
                is LatheTool.Boring ->
                    LatheToolEntity(
                        insertId = latheTool.insert.id!!,
                        type = ToolType.Boring,
                        tipOrientation = latheTool.tipOrientation.orient,
                        spindleDirection = latheTool.spindleDirection,
                        minBoreDiameter = latheTool.minBoreDiameter,
                        maxZDepth = latheTool.maxZDepth,
                    )
                is LatheTool.Drilling ->
                    LatheToolEntity(
                        type = ToolType.Drilling,
                        tipOrientation = latheTool.tipOrientation.orient,
                        spindleDirection = latheTool.spindleDirection,
                        toolDiameter = latheTool.toolDiameter,
                        maxZDepth = latheTool.maxZDepth,
                    )
                is LatheTool.Reaming ->
                    LatheToolEntity(
                        type = ToolType.Reaming,
                        tipOrientation = latheTool.tipOrientation.orient,
                        spindleDirection = latheTool.spindleDirection,
                        toolDiameter = latheTool.toolDiameter,
                        maxZDepth = latheTool.maxZDepth,
                    )
                is LatheTool.Parting ->
                    LatheToolEntity(
                        type = ToolType.Parting,
                        insertId = latheTool.insert.id!!,
                        tipOrientation = latheTool.tipOrientation.orient,
                        spindleDirection = latheTool.spindleDirection,
                        bladeWidth = latheTool.bladeWidth,
                        maxXDepth = latheTool.maxXDepth,
                    )
                is LatheTool.Grooving ->
                    LatheToolEntity(
                        type = ToolType.Grooving,
                        insertId = latheTool.insert.id!!,
                        tipOrientation = latheTool.tipOrientation.orient,
                        spindleDirection = latheTool.spindleDirection,
                        bladeWidth = latheTool.bladeWidth,
                        maxXDepth = latheTool.maxXDepth,
                    )
                is LatheTool.Slotting ->
                    LatheToolEntity(
                        type = ToolType.Slotting,
                        tipOrientation = latheTool.tipOrientation.orient,
                        spindleDirection = latheTool.spindleDirection,
                        bladeWidth = latheTool.bladeWidth,
                        maxZDepth = latheTool.maxZDepth,
                    )
                else -> return
            }
        latheToolDao.insert(entity)
    }

    override suspend fun updateLatheTool(latheTool: LatheTool) {
        val id = latheTool.toolId ?: return
        val entity: LatheToolEntity =
            when (latheTool) {
                is LatheTool.Turning ->
                    LatheToolEntity(
                        type = ToolType.Turning,
                        insertId = latheTool.insert.id!!,
                        tipOrientation = latheTool.tipOrientation.orient,
                        spindleDirection = latheTool.spindleDirection,
                    )

                is LatheTool.Boring ->
                    LatheToolEntity(
                        type = ToolType.Boring,
                        insertId = latheTool.insert.id!!,
                        tipOrientation = latheTool.tipOrientation.orient,
                        spindleDirection = latheTool.spindleDirection,
                        minBoreDiameter = latheTool.minBoreDiameter,
                        maxZDepth = latheTool.maxZDepth,
                    )

                is LatheTool.Drilling ->
                    LatheToolEntity(
                        type = ToolType.Drilling,
                        tipOrientation = latheTool.tipOrientation.orient,
                        spindleDirection = latheTool.spindleDirection,
                        toolDiameter = latheTool.toolDiameter,
                        maxZDepth = latheTool.maxZDepth,
                    )

                is LatheTool.Reaming ->
                    LatheToolEntity(
                        type = ToolType.Drilling,
                        tipOrientation = latheTool.tipOrientation.orient,
                        spindleDirection = latheTool.spindleDirection,
                        toolDiameter = latheTool.toolDiameter,
                        maxZDepth = latheTool.maxZDepth,
                    )

                is LatheTool.Parting ->
                    LatheToolEntity(
                        type = ToolType.Parting,
                        insertId = latheTool.insert.id!!,
                        tipOrientation = latheTool.tipOrientation.orient,
                        spindleDirection = latheTool.spindleDirection,
                        bladeWidth = latheTool.bladeWidth,
                        maxXDepth = latheTool.maxXDepth,
                    )

                is LatheTool.Grooving ->
                    LatheToolEntity(
                        type = ToolType.Grooving,
                        insertId = latheTool.insert.id!!,
                        tipOrientation = latheTool.tipOrientation.orient,
                        spindleDirection = latheTool.spindleDirection,
                        bladeWidth = latheTool.bladeWidth,
                        maxXDepth = latheTool.maxXDepth,
                    )

                is LatheTool.Slotting ->
                    LatheToolEntity(
                        type = ToolType.Grooving,
                        tipOrientation = latheTool.tipOrientation.orient,
                        spindleDirection = latheTool.spindleDirection,
                        bladeWidth = latheTool.bladeWidth,
                        maxZDepth = latheTool.maxZDepth,
                    )

                else -> return
            }

        latheToolDao.upsert(entity.copy(id = id))
    }

    override suspend fun deleteLatheTool(latheTool: LatheTool): Boolean {
        val id = latheTool.toolId ?: return false
        return latheToolDao.deleteById(id) != 0
    }

    private suspend fun LatheToolEntity.toLatheTool(): LatheTool? {
        val insert = insertId?.let { cuttingInsertDao.getById(it) }
        return when (type) {
            ToolType.Turning ->
                LatheTool.Turning(
                    toolId = id,
                    insert = insert!!.toCuttingInsert(),
                    tipOrientation = TipOrientation.getOrientation(tipOrientation),
                    frontAngle = frontAngle!!,
                    backAngle = backAngle!!,
                    spindleDirection = spindleDirection,
                )
            ToolType.Boring ->
                LatheTool.Boring(
                    toolId = id,
                    insert = insert!!.toCuttingInsert(),
                    tipOrientation = TipOrientation.getOrientation(tipOrientation),
                    frontAngle = frontAngle!!,
                    backAngle = backAngle!!,
                    spindleDirection = spindleDirection,
                    minBoreDiameter = minBoreDiameter ?: 0.0,
                    maxZDepth = maxZDepth ?: 0.0
                )
            ToolType.Drilling ->
                LatheTool.Drilling(
                    toolId = id,
                    insert = null,
                    toolDiameter = toolDiameter!!,
                    maxZDepth = maxZDepth ?: 0.0
                )
            ToolType.Reaming ->
                LatheTool.Reaming(
                    toolId = id,
                    insert = null,
                    toolDiameter = toolDiameter!!,
                    maxZDepth = maxZDepth ?: 0.0
                )
            ToolType.Parting ->
                LatheTool.Parting(
                    toolId = id,
                    insert = insert!!.toCuttingInsert(),
                    bladeWidth = bladeWidth!!,
                    maxXDepth = maxXDepth ?: 0.0
                )
            ToolType.Grooving ->
                LatheTool.Grooving(
                    toolId = id,
                    insert = insert!!.toCuttingInsert(),
                    tipOrientation = TipOrientation.getOrientation(tipOrientation),
                    spindleDirection = spindleDirection,
                    bladeWidth = bladeWidth!!,
                    maxXDepth = maxXDepth ?: 0.0
                )
            ToolType.OdThreading ->
                LatheTool.OdThreading(
                    toolId = id,
                    insert = insert!!.toCuttingInsert(),
                    minPitch = minThreadPitch ?: 0.0,
                    maxPitch = maxThreadPitch ?: 0.0
                )
            ToolType.IdThreading ->
                LatheTool.IdThreading(
                    toolId = id,
                    insert = insert!!.toCuttingInsert(),
                    minPitch = minThreadPitch ?: 0.0,
                    maxPitch = maxThreadPitch ?: 0.0
                )
            ToolType.Slotting ->
                LatheTool.Slotting(
                    toolId = id,
                    insert = null,
                    bladeWidth = bladeWidth!!,
                    maxZDepth = maxZDepth ?: 0.0
                )
            else -> {
                LOG
                null
            }
        }
    }

    private companion object {
        val LOG = KotlinLogging.logger("LatheToolRepository")
    }
}
