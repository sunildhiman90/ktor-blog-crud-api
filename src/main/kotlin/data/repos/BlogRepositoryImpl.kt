package com.example.data.repos

import com.example.db.entities.UserEntity
import com.example.db.entities.db.entities.BlogEntity
import com.example.db.utils.dbQuery
import com.example.domain.models.Blog
import com.example.domain.models.CreateBlogRequest
import com.example.domain.models.LoginRequest
import com.example.domain.models.RegisterRequest
import com.example.domain.models.User
import com.example.domain.models.domain.models.UpdateBlogRequest
import com.example.domain.repos.BlogRepository
import org.jetbrains.exposed.sql.Except

class BlogRepositoryImpl : BlogRepository {
    override suspend fun getAllBlogs(): List<Blog>? {
        return dbQuery {
            BlogEntity.all().map {
                Blog(it.id.value, it.title, it.content, it.author.id.value)
            }
        }
    }

    override suspend fun createBlog(
        request: CreateBlogRequest,
        authorId: Int,
    ): Blog? {
        return dbQuery {
            val authorValue = UserEntity.findById(authorId) ?: return@dbQuery null
            val blog = BlogEntity.new {
                title = request.title
                content = request.content
                author = authorValue
            }
            Blog(
                blog.id.value, blog.title, blog.content, authorId
            )
        }
    }

    override suspend fun updateBlog(
        id: Int,
        request: UpdateBlogRequest,
        authorId: Int,
    ): Blog? {
        return dbQuery {
            val blog = BlogEntity.findById(id) ?: return@dbQuery null
            if (blog.author.id.value != authorId) return@dbQuery null
            request.title?.let { blog.title = it }
            request.content?.let { blog.content = it }
            Blog(
                blog.id.value, blog.title, blog.content, authorId
            )
        }
    }

    override suspend fun deleteBlog(id: Int, authorId: Int): Boolean {
        return dbQuery {
            try {
                val blog = BlogEntity.findById(id) ?: return@dbQuery false
                if (blog.author.id.value != authorId) return@dbQuery false
                blog.delete()
                true
            } catch (e: Exception) {
                false
            }
        } == true
    }

}