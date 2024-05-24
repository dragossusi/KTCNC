package com.mindovercnc.database.table

import org.jetbrains.exposed.dao.id.IntIdTable

enum class MaterialCategory(val material: String) {
    P("Steel"),
    M("Stainless Steel"),
    K("Cast Iron"),
    N("Non Ferrous"),
    S("Super Alloys"),
    H("Hardened Steel"),
}

object WorkpieceMaterialTable : IntIdTable() {
    val name = varchar("name", 50)
    val category = varchar("category", 50)
}