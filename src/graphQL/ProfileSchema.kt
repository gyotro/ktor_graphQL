package graphQL

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import io.ktor.application.*
import models.Profile
import models.User
import services.ProfileService
import sun.rmi.runtime.Log
import java.util.logging.Level

fun SchemaBuilder.profileSchema(profileService: ProfileService) {

    type<Profile>() {
        description = "Profile type"
    }

    query("getProfile") {
        resolver { ctx: Context ->
            return@resolver try {
                // in quesot modo prendiamo l'utenza dal contesto
                val userId = ctx.get<User>()?.id ?: error("User not signed In!")
                //ctx.get<Log>()?.log(Level.INFO, "Logging get Profile")
                ctx.get<ApplicationCall>()?.application?.environment?.log?.info("Logging get Profile")
                profileService.getProfilesByUserId(userId)
            }catch (e: Exception) {null}
        }
    }
}