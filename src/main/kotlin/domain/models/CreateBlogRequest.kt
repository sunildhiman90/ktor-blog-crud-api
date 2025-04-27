package com.example.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateBlogRequest(
    val title: String,
    val content: String,
)