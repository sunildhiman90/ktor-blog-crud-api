package com.example.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Blog(
    val id: Int,
    val title: String,
    val content: String,
    val authorId: Int,
)