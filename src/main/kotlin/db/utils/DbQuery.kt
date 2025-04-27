package com.example.db.utils

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T : Any> dbQuery(block: suspend () -> T?): T? =
    newSuspendedTransaction(Dispatchers.IO) {
        block()
    }