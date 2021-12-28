package services

import com.mongodb.client.MongoClient
import models.Review
import models.ReviewInput
import org.koin.core.KoinComponent
import org.koin.core.inject
import repository.DessertRepo
import repository.ReviewRepo
import java.util.*

class ReviewService : KoinComponent{

    private val client: MongoClient by inject()
    private val repoReview = ReviewRepo(client)

    fun getReview(id: String) = repoReview.getById(id)

    fun getAll() = repoReview.getAll()

    fun addReview(user: String, dessertId: String, entry: ReviewInput) : Review =
        try {
            repoReview.add( Review(UUID.randomUUID().toString(), user, dessertId, entry.text, entry.rating) )
        }catch (e: Exception) {throw Exception("Can't add new review")}

    fun updateReview(user: String, reviewId: String, entry: ReviewInput) : Review =
        try {
                val oldReview = repoReview.getById(reviewId)
                if (oldReview.userId == user) {
                    repoReview.update(Review(oldReview.id, user, oldReview.dessertId, entry.text, entry.rating))
                }else
                    throw Exception("Can't update review")
        }catch (e: Exception) { error("Can't update review") }

    fun deleteReview(id: String, user: String) = try {
        val oldReview = repoReview.getById(id)
        if (oldReview.userId == user) {
            repoReview.deleteById(id)
        }else
            throw Exception("Can't delete review")
    }catch (e: Exception) { error("Can't delete review") }

    // fun getReviewByDessertID( dessertId: String ) = repoReview.getReviewByDessertID( dessertId )

}