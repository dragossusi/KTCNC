package com.mindovercnc.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mindovercnc.linuxcnc.tools.model.ToolType
import com.mindovercnc.model.SpindleDirection

@Entity(
    tableName = "lathe_tool",
    foreignKeys = [
        ForeignKey(
            CuttingInsertEntity::class,
            parentColumns = ["id"],
            childColumns = ["insert_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
class LatheToolEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("insert_id") val insertId: Int? = null,
    @ColumnInfo("type") val type: ToolType,
    @ColumnInfo("orientation") val tipOrientation: Int,
    @ColumnInfo("spindle_direction") val spindleDirection: SpindleDirection,
    @ColumnInfo("front_angle") val frontAngle: Double? = null,
    @ColumnInfo("back_angle") val backAngle: Double? = null,
    @ColumnInfo("tool_diameter") val toolDiameter: Double? = null,
    @ColumnInfo("min_bore_diameter") val minBoreDiameter: Double? = null,
    @ColumnInfo("blade_width") val bladeWidth: Double? = null,
    @ColumnInfo("max_z_depth") val maxZDepth: Double? = null,
    @ColumnInfo("max_x_depth") val maxXDepth: Double? = null,
    @ColumnInfo("min_thread_pitch") val minThreadPitch: Double? = null,
    @ColumnInfo("max_thread_pitch") val maxThreadPitch: Double? = null,
)