package com.example.domain.repos

import com.example.domain.models.Blog
import com.example.domain.models.CreateBlogRequest
import com.example.domain.models.RegisterRequest
import com.example.domain.models.domain.models.UpdateBlogRequest

interface BlogRepository {
    suspend fun getAllBlogs(): List<Blog>?
    suspend fun createBlog(request: CreateBlogRequest, authorId: Int): Blog?
    suspend fun updateBlog(id: Int, request: UpdateBlogRequest, authorId: Int): Blog?
    suspend fun deleteBlog(id: Int, authorId: Int): Boolean
}