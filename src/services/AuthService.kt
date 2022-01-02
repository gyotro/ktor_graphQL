package services

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mongodb.client.MongoClient
import io.ktor.application.*
import io.ktor.request.*
import models.User
import models.userInput
import models.userResponse
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import repository.UserRepository
import java.util.*

class AuthService: KoinComponent {

    private val client: MongoClient by inject()
    private val repo = UserRepository(client)
    private val secret = "secret"
    private val algorithm = Algorithm.HMAC256(secret)
    private val verifier = JWT.require(algorithm).build()

    // signIn - return a User Response if successfull
    fun signIn(userInput: userInput): userResponse? {
        getLogger().info("Signing in...")
        val user = repo.getUserByEmail(userInput.email) ?: error("User not found!")
        // hash incoming password and compare it to the saved ashed password
        if (BCrypt.verifyer().verify(userInput.password.toByteArray(),
                                     user.hashPass).verified)
        {
            val token = signAccessToken(user.id)
            getLogger().info("Signing in successfull")
            return userResponse(token, user)
        }
        else
            error("password is incorrect!")
    }


    // signUp (signing for the first time, email does not exist) - return a User response if successfull
    fun signUp(userIn: userInput): userResponse {
        // criptiamo la password
        val hashedPassword = BCrypt.withDefaults().hash(10, userIn.password.toByteArray())
        val id = UUID.randomUUID().toString()
        val email = repo.getUserByEmail(userIn.email)
        if (email != null)
            error("User email already in use!!")
        val newUser = repo.add(
            User( id = id,
                    email = userIn.email,
                    hashPass = hashedPassword)
        )
        val token = signAccessToken(id)
        getLogger().info("Signing up successfull")
        return userResponse(token, newUser)
    }

    // verifyToken - check an existing tocken and return a user model if tocken is valid
    fun verifyToken(call: ApplicationCall): User? {
        //in call abbiamo header e body della chiamata in ingresso
        return try {
            call.let {
                val autHeader = it.request.headers["Authorization"] ?: ""
                val( _, token) = autHeader.split(" ")
                getLogger().info("Session Token: $token")
                val accessToken = verifier.verify(JWT.decode(token) )
                // L'obiettivo è ottenere l'ID dal token
                val id = accessToken.getClaim("userId").asString()
                User(id = id, email = "", hashPass = ByteArray(0))
            }
        }catch (e: Exception) {
            getLogger().info("Can't parse input Token!")
            null
        }
    }

    // signAccessTocken - sign and return a valid tocken
    private fun signAccessToken(id: String): String {
        val token = JWT.create()
            .withIssuer("gyo") // a piacere
            .withClaim("userId", id)
            .sign(algorithm)
        getLogger().info("Token created: $token")
        return token
    }

    private fun getLogger(): Logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)

}