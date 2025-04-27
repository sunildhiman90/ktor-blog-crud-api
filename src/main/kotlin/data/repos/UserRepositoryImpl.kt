package com.example.data.repos

import com.example.db.entities.UserEntity
import com.example.db.utils.dbQuery
import com.example.domain.models.LoginRequest
import com.example.domain.models.RegisterRequest
import com.example.domain.models.User
import com.example.domain.repos.UserRepository
import db.tables.UsersTable
import org.mindrot.jbcrypt.BCrypt

class UserRepositoryImpl : UserRepository {
    override suspend fun createUser(request: RegisterRequest): User? {
        return dbQuery {
            val user = UserEntity.find { UsersTable.username eq request.username }.firstOrNull()
            if (user != null) {
                return@dbQuery null
            }

            return@dbQuery UserEntity.new {
                this.username = request.username
                this.password = hashPassword(request.password)
            }.let {
                User(it.id.value,  it.username)
            }

        }
    }

    override suspend fun getUserById(id: Int): User? {
        return dbQuery {
            UserEntity.findById(id)?.toUser()
        }
    }

    override suspend fun authenticate(request: LoginRequest): User? {
        return dbQuery {
            val user = UserEntity.find { UsersTable.username eq request.username }.firstOrNull()

            user?.takeIf { user ->
                verifyPassword(request.password, user.password)
            }?.let {
                User(user.id.value, user.username)
            }
        }
    }

    override fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    override fun verifyPassword(plainPassword: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(plainPassword, hashedPassword)
    }
}