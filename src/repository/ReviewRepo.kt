package repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import models.Review
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

// verranno implementate le funzioni che non sono già implementate nell'interface Repository
class ReviewRepo(client: MongoClient): RepositoryIntertface<Review>
{
    override lateinit var col: MongoCollection<Review>

    init {
        val db = client.getDatabase("testDB")
        col = db.getCollection<Review>("Review")
    }

    //get review by Dessert ID (che è l'unica funzione non contemplata già nell'interface
    fun getReviewByDessertID(dessertId: String): List<Review> =
         try {
            col.find(Review::dessertId eq dessertId)
                .asIterable()
                .map { it }
        }catch (e: Exception) { throw Exception("Can't find any review for the required dessert") }
}