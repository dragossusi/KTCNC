package com.mindovercnc.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ToolHolderAndCutter(
    @Relation(
        parentColumn = "lathe_cutter_id",
        entityColumn = "id",
    )
    val cutter: LatheToolEntity?,
    @Embedded val toolHolder: ToolHolderEntity
)
