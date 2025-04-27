package com.example.data.services.data.services

import com.example.domain.models.Blog
import com.example.domain.models.CreateBlogRequest
import com.example.domain.models.RegisterRequest
import com.example.domain.models.domain.models.UpdateBlogRequest
import com.example.domain.repos.BlogRepository
import com.example.domain.services.BlogService

class BlogServiceImpl(
    private val blogRepository: BlogRepository
) : BlogService {
    override suspend fun getAllBlogs(): List<Blog>? {
        return blogRepository.getAllBlogs()
    }

    override suspend fun createBlog(request: CreateBlogRequest, authorId: Int): Blog? {
        return blogRepository.createBlog(request, authorId)
    }

    override suspend fun updateBlog(id: Int, request: UpdateBlogRequest, authorId: Int): Blog? {
        return blogRepository.updateBlog(id, request, authorId)
    }

    override suspend fun deleteBlog(id: Int, authorId: Int): Boolean {
        return blogRepository.deleteBlog(id, authorId)
    }
}