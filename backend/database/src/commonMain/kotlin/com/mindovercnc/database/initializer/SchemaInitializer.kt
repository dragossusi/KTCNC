package com.mindovercnc.database.initializer

import com.mindovercnc.database.table.*
import initializer.InitializerStep
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

/** Initialise the database schema. */
internal class SchemaInitializer(
    private val enableLogging: Boolean
) : InitializerStep {
    override suspend fun initialise() {
        transaction {
            if (enableLogging) {
                addLogger(StdOutSqlLogger)
            }
            SchemaUtils.create(
                CuttingInsertTable,
                WorkpieceMaterialTable,
                FeedsAndSpeedsTable,
                LatheToolTable,
                ToolHolderTable
            )
        }
    }
}