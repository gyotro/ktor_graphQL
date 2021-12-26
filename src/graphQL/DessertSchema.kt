package graphQL

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import models.Dessert
import models.DessertInput
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import repository.DessertRepo

// si definisce lo schema del nostro servizio con questa extension function
fun SchemaBuilder.dessertSchema() {
    val repo = DessertRepo()

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
                return@resolver repo.getById(dessertId)
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
                repo.getAll()
            }catch (e:Exception)
            {
                emptyList()
            }

        }
    }
    mutation(name = "PostDessert") {
        description = "post new dessert"
        resolver { dessertIn: DessertInput ->
            getLogger().info("Posting new dessert: $dessertIn")
            return@resolver try {
                val dessert = Dessert(java.util.UUID.randomUUID().toString(), dessertIn.name , dessertIn.description, dessertIn.url)
                // deve ritornare il dessert creato
                repo.add(dessert)
            }catch (e:Exception) { null }
        }
    }
    mutation(name = "deleteDessert") {
        description = "delete a dessert"
        resolver { dessertId : String ->
            getLogger().info("Deleting dessert with id: $dessertId")
            return@resolver repo.deleteById(dessertId)
        }
    }

    mutation(name = "updateDessert") {
        description = "update a dessert"
        resolver { id: String, dessertIn: DessertInput ->
            getLogger().info("updating dessert with id $id and body $dessertIn")
            return@resolver try {
                val dessert = Dessert(id, dessertIn.name , dessertIn.description, dessertIn.url)
                // deve ritornare il dessert aggiornato
                repo.update(dessert)
            }catch (e:Exception) { null }
        }
    }
}

fun getLogger(): Logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)