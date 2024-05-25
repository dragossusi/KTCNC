package com.mindovercnc.linuxcnc.tools.local

import com.mindovercnc.database.dao.CuttingInsertDao
import com.mindovercnc.database.dao.LatheToolDao
import com.mindovercnc.database.entity.LatheToolEntity
import com.mindovercnc.database.table.LatheToolTable
import com.mindovercnc.linuxcnc.tools.LatheToolRepository
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolType
import com.mindovercnc.model.TipOrientation
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

/** Implementation for [LatheToolRepository]. */
class LatheToolRepositoryLocal(
    private val latheToolDao: LatheToolDao,
    private val cuttingInsertDao: CuttingInsertDao
) : LatheToolRepository {

    override suspend fun getLatheTools(): List<LatheTool> {
        return latheToolDao.getAll().mapNotNull { it.toLatheTool() }
    }

    override suspend fun getUnmountedLatheTools(): List<LatheTool> {
        TODO()
//        return transaction {
//            val toolHolderQuery =
//                ToolHolderTable.slice(ToolHolderTable.cutterId)
//                    .select(ToolHolderTable.cutterId.isNotNull())
//
//            val query = LatheToolTable.select(LatheToolTable.id notInSubQuery toolHolderQuery)
//
//            LatheToolEntity.wrapRows(query).mapNotNull { it.toLatheTool() }
//        }
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
        transaction {
            LatheToolTable.update({ LatheToolTable.id eq latheTool.toolId }) {
                when (latheTool) {
                    is LatheTool.Turning -> {
                        it[type] = ToolType.Turning
                        it[insertId] = latheTool.insert.id!!
                        it[tipOrientation] = latheTool.tipOrientation.orient
                        it[spindleDirection] = latheTool.spindleDirection
                    }
                    is LatheTool.Boring -> {
                        it[type] = ToolType.Boring
                        it[insertId] = latheTool.insert.id!!
                        it[tipOrientation] = latheTool.tipOrientation.orient
                        it[spindleDirection] = latheTool.spindleDirection
                        it[minBoreDiameter] = latheTool.minBoreDiameter
                        it[maxZDepth] = latheTool.maxZDepth
                    }
                    is LatheTool.Drilling -> {
                        it[type] = ToolType.Drilling
                        it[tipOrientation] = latheTool.tipOrientation.orient
                        it[spindleDirection] = latheTool.spindleDirection
                        it[toolDiameter] = latheTool.toolDiameter
                        it[maxZDepth] = latheTool.maxZDepth
                    }
                    is LatheTool.Reaming -> {
                        it[type] = ToolType.Drilling
                        it[tipOrientation] = latheTool.tipOrientation.orient
                        it[spindleDirection] = latheTool.spindleDirection
                        it[toolDiameter] = latheTool.toolDiameter
                        it[maxZDepth] = latheTool.maxZDepth
                    }
                    is LatheTool.Parting -> {
                        it[type] = ToolType.Parting
                        it[insertId] = latheTool.insert.id!!
                        it[tipOrientation] = latheTool.tipOrientation.orient
                        it[spindleDirection] = latheTool.spindleDirection
                        it[bladeWidth] = latheTool.bladeWidth
                        it[maxXDepth] = latheTool.maxXDepth
                    }
                    is LatheTool.Grooving -> {
                        it[type] = ToolType.Grooving
                        it[insertId] = latheTool.insert.id!!
                        it[tipOrientation] = latheTool.tipOrientation.orient
                        it[spindleDirection] = latheTool.spindleDirection
                        it[bladeWidth] = latheTool.bladeWidth
                        it[maxXDepth] = latheTool.maxXDepth
                    }
                    is LatheTool.Slotting -> {
                        it[type] = ToolType.Grooving
                        it[tipOrientation] = latheTool.tipOrientation.orient
                        it[spindleDirection] = latheTool.spindleDirection
                        it[bladeWidth] = latheTool.bladeWidth
                        it[maxZDepth] = latheTool.maxZDepth
                    }
                    else -> Unit
                }
            }
        }
    }

    override suspend fun deleteLatheTool(latheTool: LatheTool): Boolean {
        return transaction {
            LatheToolTable.deleteWhere { LatheToolTable.id eq latheTool.toolId } != 0
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
}
