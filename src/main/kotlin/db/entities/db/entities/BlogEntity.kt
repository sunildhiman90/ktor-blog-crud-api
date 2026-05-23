package com.example.db.entities.db.entities

import com.example.db.entities.UserEntity
import com.example.db.tables.db.tables.BlogsTable
import com.example.domain.models.User
import db.tables.UsersTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass


class BlogEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BlogEntity>(BlogsTable)

    var title by BlogsTable.title
    var content by BlogsTable.content
    var author by UserEntity referencedOn BlogsTable.author

}
