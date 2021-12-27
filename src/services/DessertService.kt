package services

import com.mongodb.client.MongoClient
import models.Dessert
import models.DessertInput
import org.koin.core.KoinComponent
import org.koin.core.inject
import repository.DessertRepo
import java.util.*
import javax.print.attribute.standard.JobOriginatingUserName

// la classe service contiene tutta la logica di Business
class DessertService : KoinComponent {
    private val client: MongoClient by inject()
    private val repoDessert = DessertRepo(client)

    fun getDessert(id: String) = repoDessert.getById(id)

    fun createDessert(entry: DessertInput, userName: String ) =
        repoDessert.add( Dessert(
            id = UUID.randomUUID().toString(),
            userId = userName,
            name = entry.name,
            description = entry.description,
            url = entry.url
        ) )

    fun deleteDessert(id: String, userName: String ): Boolean {
        return try {
            val initDessert = repoDessert.getById(id)
            if (initDessert.userId == userName)
                repoDessert.deleteById(id)
            else throw Exception("Can not delete item")
        }catch (e: Exception) {throw Exception("Can not delete item")}
    }

    fun updateDessert(entry: DessertInput, dessertId: String, userName: String ): Dessert {
        return try {
            val initDessert = repoDessert.getById(dessertId)
            if (initDessert.userId == userName)
            {
                val insertDessert = Dessert( id = initDessert.id,
                        userId = userName,
                        name = entry.name,
                        description = entry.description,
                        url = entry.url
                    )
                repoDessert.update(insertDessert)
            }
            else throw Exception("Can not update item")
        }catch (e: Exception) {throw Exception("Can not update item")}

    }
}