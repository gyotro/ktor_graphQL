package com.example

import com.apurebase.kgraphql.GraphQL
import di.mainModule
import graphQL.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.auth.*
import org.koin.core.context.startKoin
import services.AuthService
import services.DessertService
import services.ProfileService
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
        val service = DessertService()
        val serviceRev = ReviewService()
        val authService = AuthService()
        val profileervice = ProfileService()
        playground = true
        /*
            settiamo il contesto della chiamata con ciò che restituisce la verifyToken, ovvero un User?
           il log della chiamata
           la chiamata stessa
         */
        context {
                call: ApplicationCall -> authService.verifyToken(call)?.let { +it }
            +log
            +call
        }
        schema {
/*            query("hello") {
                resolver {  -> "World" }
            }*/
            reviewSchema(serviceRev)
            dessertSchema(service)
            authSchema(authService)
            profileSchema(profileervice)
        }
    }
}