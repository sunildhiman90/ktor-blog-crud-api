package com.example.routes


import io.ktor.http.CacheControl
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import java.io.File

fun Application.staticContent() {
    routing {
        authenticate("auth-jwt") {

            staticFiles("/resources", File("files/private"))

        }


        staticFiles("/public_resources", File("files")) {

            exclude { file ->
                file.path.contains("excluded")
            }

            cacheControl { file ->
                when (file.name) {
                    "logs1.txt" -> listOf(CacheControl.MaxAge(10000))
                    else -> emptyList()
                }

            }
        }

        staticResources("/", "static") {
            //script.js.gz
            preCompressed(
                CompressedFileType.GZIP
            )

        }

    }
}