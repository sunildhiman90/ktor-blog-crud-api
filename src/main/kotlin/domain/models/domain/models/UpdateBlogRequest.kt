package com.example.domain.models.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UpdateBlogRequest(
    val title: String? = null,
    val content: String? = null,
)