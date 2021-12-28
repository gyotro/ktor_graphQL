package models

data class Dessert(override val id: String,
                   var userId : String,
                   var name: String,
                   var description: String,
                   var url: String,
                   var reviews: List<Review> = emptyList()
                    ): Model

// data classes used for paging
data class PagingInfo( var count: Long, var pages: Int, var next: Int?, var prev: Int? )

data class DessertsPage( var result: List<Dessert>, val info: PagingInfo )