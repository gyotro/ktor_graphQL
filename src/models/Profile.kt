package models

// classe che associa user ed i dessert a lui associati
data class Profile( val user: User, val desserts: List<Dessert> = emptyList() )