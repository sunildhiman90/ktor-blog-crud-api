package db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object UsersTable : IntIdTable() {
    val username = varchar("username", 100).uniqueIndex()
    val password = varchar("password", 100)
}