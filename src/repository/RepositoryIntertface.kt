package repository

import com.mongodb.client.MongoCollection
import models.Model
import org.litote.kmongo.*

// si userà solo questa interface per le query a DB
interface RepositoryIntertface<T> {
    val col: MongoCollection<T>
    fun getById(id: String): T {
        return try {
            col.findOne(Model::id eq id) ?: throw Exception ("No item with that ID exists!")
            //col.findOneById(id) ?: throw Exception ("No item with that ID exists!")
        }catch (t: Throwable) {throw Exception("No item with that ID exists!")}
    }
    fun getAll(): List<T> {
        return try {
            col.find()
                .asIterable()
                .map { it }
            //col.findOneById(id) ?: throw Exception ("No item with that ID exists!")
        }catch (e: Exception) {
            emptyList()
        }
    }
    fun deleteById(id: String): Boolean {
        return try {
            col.findOneAndDelete(Model::id eq id) ?: throw Exception ("No item with that ID exists!")
            true//col.findOneById(id) ?: throw Exception ("No item with that ID exists!")
        }catch (t: Throwable) { false }
    }
    fun add(entry: T): T {
        return try {
            col.insertOne(entry) ?: throw Exception ("Can't add new entry")
            entry
        }catch (t: Throwable) { throw Exception ("Can't add new entry") }
    }
    fun update(entry: Model): T {
        return try {
            col.updateOne(Model::id eq entry.id, entry)
            // diamo in output quello che abbiamo aggiornato nella riga prima
            col.findOne(Model::id eq entry.id) ?: throw Exception ("Can't update item")
        }catch (t: Throwable) { throw Exception ("Can't update item") }
    }
}