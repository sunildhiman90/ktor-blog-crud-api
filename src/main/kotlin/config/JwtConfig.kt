package com.example.config

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.domain.models.User
import io.ktor.server.application.Application
import java.util.Date

fun Application.generateToken(user: User): String? {

    return try {

        val jwtSecret = System.getenv("jwt_secret")
        val jwtIssuer = environment.config.property("jwt.issuer").getString()
        val jwtAudience = environment.config.property("jwt.audience").getString()
        val claimField = environment.config.property("jwt.claimField").getString()
        val expTimeInMins = 24 * 60
        val expTimeInMillis = expTimeInMins * 60 * 1000

        return JWT.create()
            .withIssuer(jwtIssuer)
            .withAudience(jwtAudience)
            .withClaim(claimField, user.id)
            .withExpiresAt(
                Date(
                    System.currentTimeMillis() + expTimeInMillis
                )
            )
            .sign(
                Algorithm.HMAC256(jwtSecret)
            )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

}

fun Application.jwtVerifier(): JWTVerifier {
    val jwtSecret = System.getenv("jwt_secret")
    val jwtIssuer = environment.config.property("jwt.issuer").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()

    return JWT.require(Algorithm.HMAC256(jwtSecret))
        .withIssuer(jwtIssuer)
        .withAudience(jwtAudience)
        .build()
}