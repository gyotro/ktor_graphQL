package models

data class Review(override val id: String,
                            val userId: String,
                            val dessertId: String,
                            val text: String,
                            val rating: String) : Model

data class ReviewInput(val text: String, val rating: String)