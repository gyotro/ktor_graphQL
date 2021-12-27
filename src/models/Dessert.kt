package models

data class Dessert(override val id: String,
                   var userId : String,
                   var name: String,
                   var description: String,
                   var url: String): Model