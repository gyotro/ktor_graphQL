package repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import models.User
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

class UserRepository(val client: MongoClient) : RepositoryIntertface<User> {

    override lateinit var col: MongoCollection<User>


    init {
        val dbMongo = client.getDatabase("testDB")
        col = dbMongo.getCollection<User>("User")
    }

    // getUserByEmail
    fun getUserByEmail(email: String? = null) : User? {
        return try {
            col.findOne(User::email eq email)
        }catch (e: Exception) {
            throw Exception("User not found!")
        }
    }

}