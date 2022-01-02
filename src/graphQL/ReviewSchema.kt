package graphQL

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import models.Review
import models.ReviewInput
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import services.ReviewService

fun SchemaBuilder.reviewSchema(reviewService : ReviewService) {
    // Definiamo i tipi (input e output)
    inputType<ReviewInput>(){
        description = "Review input without ID"
    }

    type<Review>() {
        description = "Review type"
    }

    query(name = "getReview") {
        description = "get review by ID"
        resolver {
            id: String -> try {
                reviewService.getReview(id)
            }catch (e: Exception) {null}
        }
    }
    mutation(name = "postReview") {
        description = "post new review"
        resolver { dessertId: String, reviewIn: ReviewInput, ctx: Context ->
            getLogger().info("Posting new dessert: $reviewIn")
            return@resolver try {
                val user = "gyo"
                reviewService.addReview(user, dessertId, reviewIn)
            }catch (e:Exception) { null }
        }
    }
    mutation(name = "updateReview") {
        description = "update existing review"
        resolver { reviewId: String, entry: ReviewInput ->
            getLogger().info("Posting new dessert: $entry")
            return@resolver try {
                val user = "gyo"
                reviewService.updateReview(user, reviewId, entry)
            }catch (e:Exception) { null }
        }
    }
    mutation(name = "deleteReview") {
        description = "delete review"
        resolver {
                id: String -> try {
            val user = "gyo"
            reviewService.deleteReview(id, user)
        }catch (e: Exception) { false }
        }
    }
}

private fun getLogger(): Logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)