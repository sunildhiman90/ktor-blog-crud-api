package com.example

import com.example.di.configureKoin
import com.example.domain.services.BlogService
import com.example.domain.services.UserService
import com.example.routes.blogRoutes
import com.example.routes.staticContent
import com.example.routes.userRoutes
import com.example.security.configSecurity
import initDB
import io.ktor.server.application.*
import org.koin.ktor.ext.get

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureContentNegotiation()
    initDB()

    val userService = get<UserService>()
    val blogService = get<BlogService>()
    configSecurity(userService)
    userRoutes(userService)
    blogRoutes(blogService)
    staticContent()
}
