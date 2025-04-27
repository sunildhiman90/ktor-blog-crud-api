package com.example.domain.services

import com.example.domain.models.LoginRequest
import com.example.domain.models.RegisterRequest
import com.example.domain.models.User

interface UserService {
    suspend fun registerUser(registerRequest: RegisterRequest): User?
    suspend fun loginUser(request: LoginRequest): User?
    suspend fun getUserById(id: Int): User?
}