package models

// 1 : Come verrà scritto a DB
data class User(override val id: String, val email: String, val hashPass: ByteArray) : Model

// 2 : Come verrà inviato dall'app
data class userInput(val email: String, val password: String)

// 3 : la response che riceverà l'utente
data class userResponse(val token: String, val user: User)
