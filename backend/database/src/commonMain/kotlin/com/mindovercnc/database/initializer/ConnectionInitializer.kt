package com.mindovercnc.database.initializer

import initializer.InitializerStep
import okio.Path
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.Connection

internal class ConnectionInitializer(private val databasePath: Path) : InitializerStep {
    override suspend fun initialise() {
        Database.connect(url = "jdbc:sqlite:$databasePath", driver = "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    }
}
