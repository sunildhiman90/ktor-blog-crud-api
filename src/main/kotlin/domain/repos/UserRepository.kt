package com.example.domain.repos

import com.example.domain.models.LoginRequest
import com.example.domain.models.RegisterRequest
import com.example.domain.models.User

interface UserRepository {
    suspend fun createUser(request: RegisterRequest): User?
    suspend fun getUserById(id: Int): User?
    suspend fun authenticate(request: LoginRequest): User?
    fun hashPassword(password: String): String
    fun verifyPassword(plainPassword: String, hashedPassword: String): Boolean
}