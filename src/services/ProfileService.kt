package services

import com.mongodb.client.MongoClient
import models.Profile
import models.User
import org.koin.core.KoinComponent
import org.koin.core.inject
import repository.DessertRepo
import repository.UserRepository

class ProfileService : KoinComponent {

    private val client: MongoClient by inject()
    private val repoDessert = DessertRepo(client)
    private val repoUser = UserRepository(client)

    fun getProfilesByUserId(id: String): Profile {
        return try {
            val user = repoUser.getById(id)
            val desserts = repoDessert.getDessertByUserId(id)
            Profile(user, desserts)
        }catch (e: Exception) {throw Exception("Incorrect User ID!")}
    }
}