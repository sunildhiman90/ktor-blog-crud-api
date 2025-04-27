package com.example.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String
)