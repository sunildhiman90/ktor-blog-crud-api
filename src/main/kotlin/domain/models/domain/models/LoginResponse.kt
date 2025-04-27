package com.example.domain.models.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val id: String,
    val username: String,
    val token: String,
)