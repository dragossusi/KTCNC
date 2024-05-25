package com.mindovercnc.database.initializer

import com.mindovercnc.database.KtcncDatabase
import com.mindovercnc.database.MaterialCategory
import com.mindovercnc.database.entity.CuttingInsertEntity
import com.mindovercnc.database.entity.LatheToolEntity
import com.mindovercnc.database.entity.ToolHolderEntity
import com.mindovercnc.database.entity.WorkpieceMaterialEntity
import com.mindovercnc.linuxcnc.tools.model.MadeOf
import com.mindovercnc.linuxcnc.tools.model.ToolHolderType
import com.mindovercnc.linuxcnc.tools.model.ToolType
import com.mindovercnc.model.SpindleDirection
import com.mindovercnc.model.TipOrientation
import initializer.InitializerStep
import kotlin.random.Random

internal class DummyToolsInitializer(private val database: KtcncDatabase, private val count: Int) :
    InitializerStep {

    object Inserts {
        val CCMT = CuttingInsertEntity(
            madeOf = MadeOf.Carbide,
            code = "CCMT",
            tipRadius = 0.8,
            tipAngle = 80.0,
            size = 9.5,
        )
        val DCMT = CuttingInsertEntity(
            madeOf = MadeOf.Carbide,
            code = "DCMT",
            tipRadius = 0.8,
            tipAngle = 55.0,
            size = 9.5,
        )
        val VCMT = CuttingInsertEntity(
            madeOf = MadeOf.Carbide,
            code = "VCMT",
            tipRadius = 0.8,
            tipAngle = 35.0,
            size = 9.5,
        )
        val TCMT = CuttingInsertEntity(
            madeOf = MadeOf.Carbide,
            code = "TCMT",
            tipRadius = 0.8,
            tipAngle = 60.0,
            size = 13.0,
        )
    }

    override suspend fun initialise() {
        if (database.cuttingInsertDao.getCount() == 0L) {
            createDummyInserts()
        }
        if (database.latheToolDao.getCount() == 0L) {
            createDummyTools()
        }
        if (database.toolHolderDao.getCount() == 0L) {
            createDummyHolders()
        }
        if (database.workPieceMaterialDao.getCount() == 0L) {
            createWorkpieceMaterials()
        }
    }

    private suspend fun createDummyHolders() {
        val types = ToolHolderType.entries
        val items = List(count) {
            ToolHolderEntity(
                holderNumber = it + 1,
                holderType = types.random(),
                cutterId = null,
                clampingPosition = 0,
                xOffset = Random.nextDouble(),
                zOffset = Random.nextDouble(),
            )
        }
        database.toolHolderDao.insertAll(items)
    }

    private suspend fun createDummyTools() {
        LatheToolEntity(
            insertId = getInsertByCode("DCMT").id,
            type = ToolType.Turning,
            tipOrientation = TipOrientation.Position2.orient,
            frontAngle = 0.0,
            backAngle = 0.0,
            spindleDirection = SpindleDirection.Reverse,
        )
        LatheToolEntity(
            insertId = getInsertByCode("VCMT").id,
            type = ToolType.Turning,
            tipOrientation = TipOrientation.Position2.orient,
            frontAngle = 0.0,
            backAngle = 0.0,
            spindleDirection = SpindleDirection.Reverse,
        )
        LatheToolEntity(
            insertId = getInsertByCode("CCMT").id,
            type = ToolType.Boring,
            tipOrientation = TipOrientation.Position3.orient,
            frontAngle = 0.0,
            backAngle = 0.0,
            spindleDirection = SpindleDirection.Reverse,
            minBoreDiameter = 20.0,
            maxZDepth = 50.0,
        )
        LatheToolEntity(
            insertId = getInsertByCode("VCMT").id,
            tipOrientation = TipOrientation.Position7.orient,
            spindleDirection = SpindleDirection.Reverse,
            type = ToolType.Parting,
            bladeWidth = 2.0,
            maxXDepth = 20.0,
        )
        LatheToolEntity(
            type = ToolType.Drilling,
            tipOrientation = TipOrientation.Position7.orient,
            spindleDirection = SpindleDirection.Reverse,
            toolDiameter = 8.0,
            maxZDepth = 80.0,
        )
    }

    private suspend fun getInsertByCode(code: String): CuttingInsertEntity {
        return database.cuttingInsertDao.getByCode(code)
    }

    private suspend fun createDummyInserts() {
        val entities = listOf(
            Inserts.CCMT,
            Inserts.DCMT,
            Inserts.VCMT,
            Inserts.TCMT,
        )
        database.cuttingInsertDao.insertAll(entities)
    }

    private suspend fun createWorkpieceMaterials() {
        val materials = listOf(
            WorkpieceMaterialEntity(
                name = "Steel",
                category = MaterialCategory.P.name,
            ),
            WorkpieceMaterialEntity(
                name = "Stainless Steel",
                category = MaterialCategory.M.name,
            ),
            WorkpieceMaterialEntity(
                name = "Cast Iron",
                category = MaterialCategory.K.name,
            ),
            WorkpieceMaterialEntity(
                name = "Non Ferrous",
                category = MaterialCategory.N.name,
            ),
            WorkpieceMaterialEntity(
                name = "Super Alloy",
                category = MaterialCategory.S.name,
            ),
            WorkpieceMaterialEntity(
                name = "Super Alloy",
                category = MaterialCategory.S.name,
            ),
        )
        database.workPieceMaterialDao.insertAll(materials)
    }
}
