package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.apurebase.kgraphql.GraphQL
import di.mainModule
import graphQL.dessertSchema
import io.ktor.application.*
import org.koin.core.context.startKoin
import services.DessertService

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
        schema {
/*            query("hello") {
                resolver {  -> "World" }
            }*/
            dessertSchema(service)
        }
    }
}