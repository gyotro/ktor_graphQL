package di

import com.example.env
import com.mongodb.ConnectionString
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import org.koin.dsl.module
import org.litote.kmongo.KMongo

// in questo file è presente il modulo inerente alla Dipendency Injection al DB

val mainModule = module(createdAtStart = true) {
    factory {
        //KMongo.createClient(env ?: "" )
        KMongo.createClient( System.getenv("MONGO_URI").toString() )
    }
}
