package com.example.data.services.data.services

import com.example.domain.models.LoginRequest
import com.example.domain.models.RegisterRequest
import com.example.domain.models.User
import com.example.domain.repos.UserRepository
import com.example.domain.services.UserService

class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {
    override suspend fun registerUser(registerRequest: RegisterRequest): User? {
        return userRepository.createUser(registerRequest)
    }
    override suspend fun loginUser(request: LoginRequest): User? {
        return userRepository.authenticate(request)
    }
    override suspend fun getUserById(id: Int): User? {
        return userRepository.getUserById(id)
    }
}