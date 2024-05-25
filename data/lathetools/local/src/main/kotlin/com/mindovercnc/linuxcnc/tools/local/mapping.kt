package com.mindovercnc.linuxcnc.tools.local

import com.mindovercnc.database.entity.CuttingInsertEntity
import com.mindovercnc.database.entity.WorkpieceMaterialEntity
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.WorkpieceMaterial

fun CuttingInsertEntity.toCuttingInsert(): CuttingInsert {
    return CuttingInsert(
        id = id,
        madeOf = madeOf,
        code = code,
        tipRadius = tipRadius,
        tipAngle = tipAngle,
        size = size
    )
}

fun WorkpieceMaterialEntity.toWorkpieceMaterial(): WorkpieceMaterial {
    return WorkpieceMaterial(
        id = id,
        name = name,
    )
}
