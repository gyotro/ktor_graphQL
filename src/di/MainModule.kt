package di

import com.mongodb.ConnectionString
import org.koin.dsl.module
import org.litote.kmongo.KMongo

// in questo file è presente il modulo inerente alla Dipendency Injection al DB

val mainModule = module(createdAtStart = true) {
    factory {
        KMongo.createClient(System.getenv("MONGO_URI") ?: "" )
    }
}