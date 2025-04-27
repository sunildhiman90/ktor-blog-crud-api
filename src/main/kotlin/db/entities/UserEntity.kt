package com.example.db.entities

import com.example.domain.models.User
import db.tables.UsersTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID


class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(UsersTable)

    var username by UsersTable.username
    var password by UsersTable.password

    fun toUser(): User? = User(
        id.value, username
    )

}
