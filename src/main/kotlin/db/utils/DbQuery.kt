package com.example.db.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

suspend fun <T : Any> dbQuery(block: suspend () -> T?): T? =
    withContext(Dispatchers.IO) {
        suspendTransaction {
            block()
        }
    }