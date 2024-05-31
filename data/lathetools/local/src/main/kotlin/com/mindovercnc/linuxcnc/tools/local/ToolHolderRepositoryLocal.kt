package com.mindovercnc.linuxcnc.tools.local

import com.mindovercnc.database.dao.CuttingInsertDao
import com.mindovercnc.database.dao.ToolHolderDao
import com.mindovercnc.database.entity.LatheToolEntity
import com.mindovercnc.database.entity.ToolHolderEntity
import com.mindovercnc.linuxcnc.tools.ToolHolderRepository
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import com.mindovercnc.linuxcnc.tools.model.ToolType
import com.mindovercnc.model.TipOrientation

/** Implementation for [ToolHolderRepository]. */
class ToolHolderRepositoryLocal(
    private val toolHolderDao: ToolHolderDao,
    private val cuttingInsertDao: CuttingInsertDao
) : ToolHolderRepository {

    override suspend fun getToolHolders(): List<ToolHolder> {
        return toolHolderDao.getAllWithCutter().map { (cutter, toolHolder) ->
                ToolHolder(
                    holderNumber = toolHolder.holderNumber,
                    type = toolHolder.holderType,
                    latheTool = cutter?.toLatheTool(),
                    xOffset = toolHolder.xOffset,
                    zOffset = toolHolder.zOffset
                )
            }
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
            else -> null
        }
    }

    override suspend fun createToolHolder(toolHolder: ToolHolder) {
        val entity =
            ToolHolderEntity(
                holderNumber = toolHolder.holderNumber,
                holderType = toolHolder.type,
                cutterId = null,
                clampingPosition = 0,
                xOffset = toolHolder.xOffset,
                zOffset = toolHolder.zOffset,
            )
        toolHolderDao.insert(entity)
    }

    override suspend fun updateToolHolder(toolHolder: ToolHolder) {
        val entity = ToolHolderEntity(
            holderNumber = toolHolder.holderNumber,
            holderType = toolHolder.type,
            cutterId = toolHolder.latheTool?.toolId,
            clampingPosition = toolHolder.clampingPosition,
            // TODO offsets in a separate call
            xOffset = toolHolder.xOffset,
            zOffset = toolHolder.zOffset
        )
        toolHolderDao.update(entity)
    }

    override suspend fun deleteToolHolder(toolHolder: ToolHolder): Boolean {
        return toolHolderDao.deleteByNumber(toolHolder.holderNumber) != 0
    }
}
