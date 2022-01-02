package com.example

import com.apurebase.kgraphql.GraphQL
import di.mainModule
import graphQL.reviewSchema
import graphQL.authSchema
import graphQL.reviewSchema
import graphQL.dessertSchema
import io.ktor.application.*
import io.ktor.http.auth.*
import org.koin.core.context.startKoin
import services.AuthService
import services.DessertService
import services.ReviewService

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    // inizializziamo il modulo per KMONGO
    startKoin {
        modules(mainModule)
    }

    install(GraphQL) {
        playground = true
        val service = DessertService()
        val serviceRev = ReviewService()
        val authService = AuthService()
        schema {
/*            query("hello") {
                resolver {  -> "World" }
            }*/
            reviewSchema(serviceRev)
            dessertSchema(service)
            authSchema(authService)
        }
    }
}