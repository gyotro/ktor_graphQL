package graphQL

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import models.Dessert
import models.DessertInput
import models.DessertsPage
import models.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import repository.DessertRepo
import services.DessertService

// si definisce lo schema del nostro servizio con questa extension function
fun SchemaBuilder.dessertSchema(service: DessertService) {

    // Definiamo i tipi (input e output)
    inputType<DessertInput>(){
        description = "Dessert input without ID"
    }

    type<Dessert>() {
        description = "Dessert type"
    }

    // il nome è importante xke con questo nome andrà invocata
    query( name = "dessert", init = {
        description = "get dessert by ID"
        // il valore che andrà passato sarà dessertId
        resolver { dessertId : String ->
            getLogger().info("Retrieving dessert with id: $dessertId")
            return@resolver try {
                service.getDessert(dessertId)
            }catch (e:Exception)
            {
                null
            }
        }
    })

    query( name = "desserts") {
        description = "get all desserts"
        resolver { ->
            getLogger().info("Retrieving all desserts")
            return@resolver try {
                service.getAll()
            }catch (e:Exception)
            {
                emptyList()
            }
        }
    }

    query( name = "dessertsPage") {
        description = "get all desserts with pagination"
        resolver { page: Int, size: Int ->
            getLogger().info("Retrieving all desserts in a page")
            return@resolver try {
                service.getDessertPage(page, size)
            }catch (e:Exception)
            {
                null
            }
        }
    }

    mutation(name = "PostDessert") {
        description = "post new dessert"
        resolver { dessertIn: DessertInput, ctx: Context ->
            getLogger().info("Posting new dessert: $dessertIn")
            return@resolver try {
                val user = ctx.get<User>()?.id ?: error("User not signed In!")
                service.createDessert(dessertIn, user)
            }catch (e:Exception) { null }
        }
    }
    mutation(name = "deleteDessert") {
        description = "delete a dessert"
        resolver { dessertId : String, ctx: Context ->
            getLogger().info("Deleting dessert with id: $dessertId")
            val user = ctx.get<User>()?.id ?: error("User not signed In!")
            return@resolver service.deleteDessert(dessertId, user)
        }
    }

    mutation(name = "updateDessert") {
        description = "update a dessert"
        resolver { id: String, dessertIn: DessertInput, ctx: Context ->
            getLogger().info("updating dessert with id $id and body $dessertIn")
            return@resolver try {
                val user = ctx.get<User>()?.id ?: error("User not signed In!")
                // deve ritornare il dessert aggiornato
                service.updateDessert(dessertIn, id, user)
            }catch (e:Exception) { null }
        }
    }
}

private fun getLogger(): Logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)