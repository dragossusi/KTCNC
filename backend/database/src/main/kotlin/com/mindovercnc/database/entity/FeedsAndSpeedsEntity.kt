package com.mindovercnc.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CuttingInsertEntity::class,
            parentColumns = ["id"],
            childColumns = ["insert_id"]
        ),
        ForeignKey(
            entity = WorkpieceMaterialEntity::class,
            parentColumns = ["id"],
            childColumns = ["material_id"]
        ),
    ]
)
data class FeedsAndSpeedsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("insert_id") val insertId: Int,
    @ColumnInfo("material_id") val materialId: Int,
//    var insert : CuttingInsertEntity referencedOn FeedsAndSpeedsTable.insertId
//    var material : WorkpieceMaterialEntity referencedOn FeedsAndSpeedsTable.materialId
    @ColumnInfo("css_min") val cssMin: Double,
    @ColumnInfo("css_max") val cssMax: Double,
    @ColumnInfo("feed_min") val feedMin: Double,
    @ColumnInfo("feed_max") val feedMax: Double,
    @ColumnInfo("doc_min") val docMin: Double,
    @ColumnInfo("doc_max") val docMax: Double,
)