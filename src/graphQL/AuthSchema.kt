package graphQL

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import models.Dessert
import models.DessertInput
import models.User
import models.userInput
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import services.AuthService

fun SchemaBuilder.authSchema(authService: AuthService) {

    inputType<userInput>(){
        description = "User input"
    }

    type<User>() {
        description = "User type without hashPass (because final users must not visualize the hashPass)"
        User::hashPass.ignore()
    }

    mutation("signIn") {
        description = "Authenticate an existing User"
        resolver { userInput: userInput ->
             try { authService.signIn(userInput)
            } catch (e: Exception) {null}
        }
    }

    mutation("signUp") {
        description = "Authenticate a new User"
        resolver { userInput: userInput ->
            try { authService.signUp(userInput)
            } catch (e: Exception) {null}
        }
    }
}

private fun getLogger(): Logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)