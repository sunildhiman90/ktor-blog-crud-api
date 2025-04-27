package com.example.routes

import com.example.config.generateToken
import com.example.domain.models.CreateBlogRequest
import com.example.domain.models.LoginRequest
import com.example.domain.models.RegisterRequest
import com.example.domain.models.User
import com.example.domain.models.domain.models.LoginResponse
import com.example.domain.models.domain.models.UpdateBlogRequest
import com.example.domain.services.BlogService
import com.example.domain.services.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.blogRoutes(blogService: BlogService) {
    routing {
        authenticate("auth-jwt") {
            route("/api/blogs") {
                get {
                    val blogs = blogService.getAllBlogs()
                    if (blogs != null) {
                        call.respond(HttpStatusCode.OK, blogs)
                    } else {
                        call.respond(HttpStatusCode.BadGateway, "Please try after some time")
                    }
                }

                post {

                    val request = call.receive<CreateBlogRequest>()
                    val user = call.authentication.principal<User>()

                    if (user != null) {
                        val blog = blogService.createBlog(request, user.id)
                        if (blog != null) {
                            call.respond(HttpStatusCode.Created, blog)
                        } else {
                            call.respond(HttpStatusCode.Conflict, "Failed to create blog")
                        }
                    } else {
                        call.respond(HttpStatusCode.Unauthorized, "User not authenticated")
                    }
                }

                put("/{id}") {

                    val id = call.parameters["id"]?.toIntOrNull()
                    if(id == null) {
                        call.respond(HttpStatusCode.BadRequest, "invalid id")
                        return@put
                    }

                    val request = call.receive<UpdateBlogRequest>()
                    val user = call.authentication.principal<User>()

                    if (user != null) {
                        val blog = blogService.updateBlog(id, request, user.id)
                        if (blog != null) {
                            call.respond(HttpStatusCode.OK, blog)
                        } else {
                            call.respond(HttpStatusCode.NotFound, "Failed to update blog, blog not found")
                        }
                    } else {
                        call.respond(HttpStatusCode.Unauthorized, "User not authenticated")
                    }
                }

                delete("/{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    if(id == null) {
                        call.respond(HttpStatusCode.BadRequest, "invalid id")
                        return@delete
                    }

                    val user = call.authentication.principal<User>()

                    if (user != null) {
                        val deleted = blogService.deleteBlog(id, user.id)
                        if (deleted) {
                            call.respond(HttpStatusCode.OK, "blog deleted successfully!")
                        } else {
                            call.respond(HttpStatusCode.NotFound, "Failed to delete blog, blog not found")
                        }
                    } else {
                        call.respond(HttpStatusCode.Unauthorized, "User not authenticated")
                    }


                }


            }



        }
    }
}