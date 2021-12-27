package repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import models.Dessert
import org.litote.kmongo.getCollection

class DessertRepo(client: MongoClient) : RepositoryIntertface<Dessert> {
    override lateinit var col: MongoCollection<Dessert>

    init {
        val db = client.getDatabase("testDB")
        col = db.getCollection<Dessert>("Dessert")
    }

}