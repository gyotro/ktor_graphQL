package repository

import data.initDessert
import models.Dessert

class DessertRepo : RepositoryIntertface<Dessert> {
    override fun getById(id: String): Dessert = initDessert.filter { it.id == id }.first()

    override fun getAll(): List<Dessert> = initDessert

    override fun deleteById(id: String): Boolean {
        return try {
            initDessert.removeIf { it.id == id }
        } catch (e: Exception) {
            false
        }
    }

    override fun add(entry: Dessert): Dessert {
        initDessert.add(entry)
        return entry
    }

    override fun update(entry: Dessert): Dessert {
            return initDessert.find { it.id == entry.id }?.apply {
                name = entry.name
                description = entry.description
                url = entry.url
            } ?: throw Exception("No Dessert with that ID is present!!")
        }

}