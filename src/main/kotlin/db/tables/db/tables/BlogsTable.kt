package com.example.db.tables.db.tables

import db.tables.UsersTable
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object BlogsTable : IntIdTable("blogs") {
    val title = varchar("title", 100)
    val content = text("content")

    val author = reference(
        name = "author_id",
        foreign = UsersTable,
        onDelete = ReferenceOption.CASCADE
    )
}