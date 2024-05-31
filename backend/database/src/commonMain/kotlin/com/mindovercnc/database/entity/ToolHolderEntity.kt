package com.mindovercnc.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mindovercnc.linuxcnc.tools.model.ToolHolderType

@Entity(
    tableName = "tool_holder",
    foreignKeys =
        [
            ForeignKey(
                LatheToolEntity::class,
                parentColumns = ["id"],
                childColumns = ["lathe_cutter_id"]
            )
        ]
)
data class ToolHolderEntity(
    @PrimaryKey @ColumnInfo("holder_number") val holderNumber: Int,
    @ColumnInfo("holder_type") val holderType: ToolHolderType,
    @ColumnInfo("clamping_position") val clampingPosition: Int,
    @ColumnInfo("x_offset") val xOffset: Double?,
    @ColumnInfo("z_offset") val zOffset: Double?,
    @ColumnInfo("lathe_cutter_id") val cutterId: Int?,
)
