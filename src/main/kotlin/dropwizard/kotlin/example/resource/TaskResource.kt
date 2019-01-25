package dropwizard.kotlin.example.resource

import dropwizard.kotlin.example.api.User
import java.util.concurrent.ConcurrentHashMap
import javax.validation.constraints.NotNull
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/task")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class TaskResource {
    private val userDb = ConcurrentHashMap<String, User>()

    private val map = mapOf(1 to 3, 3 to 100, 5 to 349)


    @GET
    fun get(@QueryParam("task_name") username: String): Response {
        if (userDb.containsKey(username)) {
            return Response.ok().entity(userDb[username]).build()
        }
        return Response.noContent().build()
    }

    @PUT
    fun put(@NotNull user: User): Response {
        userDb[user.username] = user
        return Response.ok().build()
    }

    @POST
    fun post(@NotNull user: User): Response {
        userDb[user.username] = user
        return Response.ok().build()
    }

    @DELETE
    fun delete(@NotNull @QueryParam("username") username: String): Response {
        userDb.remove(username)
        return Response.ok().build()
    }

}
