package repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import models.Dessert
import models.DessertsPage
import models.PagingInfo
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class DessertRepo(client: MongoClient) : RepositoryIntertface<Dessert> {
    override lateinit var col: MongoCollection<Dessert>

    init {
        val db = client.getDatabase("testDB")
        col = db.getCollection<Dessert>("Dessert")
    }

    // implementing pagination
    fun getDessertPage(page: Int, size: Int ): DessertsPage {
        return try {
            val skips = page * size
            val res = col.find().skip(skips).limit(size)
            val results = res.asIterable().map { it }
            val totalDessert = col.estimatedDocumentCount()
            val totalPages = (totalDessert / size) + 1
            val next = if (results.isNotEmpty() && page < totalPages) page + 1 else null
            val prev = if (page > 0) page - 1 else null
            val pageInfo = PagingInfo( totalPages, page, next, prev )
            DessertsPage(results, pageInfo)
        }catch (e: Exception) { throw Exception("Can't get required page!") }
    }

    fun getDessertByUserId(id: String): List<Dessert>
    {
        return try {
            col.find(Dessert::id eq id).asIterable().map { it }
        }catch (e: Exception) {
            throw Exception("Can not get user desserts!")
        }
    }

}