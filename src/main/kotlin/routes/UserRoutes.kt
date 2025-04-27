package com.example.routes

import com.example.config.generateToken
import com.example.domain.models.LoginRequest
import com.example.domain.models.RegisterRequest
import com.example.domain.models.domain.models.LoginResponse
import com.example.domain.services.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.userRoutes(userService: UserService) {
    routing {
        route("/api/users") {

            post("/register") {
                val reguest = call.receive<RegisterRequest>()
                val user = userService.registerUser(reguest)
                if (user != null) {
                    call.respond(HttpStatusCode.Created, user)
                } else {
                    call.respond(HttpStatusCode.Conflict, "Username already exists")
                }
            }

            post("/login") {
                val reguest = call.receive<LoginRequest>()
                val user = userService.loginUser(reguest)
                if (user != null) {
                    val token = generateToken(user)
                    if (token != null) {
                        val res = LoginResponse(
                            id = user.id.toString(),
                            username = user.username,
                            token = token
                        )
                        call.respond(HttpStatusCode.OK, res)
                    } else {
                        call.respond(HttpStatusCode.Unauthorized, "Invalid Credentials")
                    }
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid Credentials")
                }
            }

            get("/{id}") {

                val id = call.parameters["id"]?.toIntOrNull()
                if(id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid id format")
                    return@get
                }

                val user = userService.getUserById(id)
                if (user != null) {
                    call.respond(HttpStatusCode.OK, user)
                } else {
                    call.respond(HttpStatusCode.NotFound, "User not found")
                }

            }
        }
    }
}