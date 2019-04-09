package dropwizard.kotlin.example.resources

import dropwizard.kotlin.example.api.User
import dropwizard.kotlin.example.filter.Secured
import dropwizard.kotlin.example.managers.UserManager
import java.util.*
import javax.validation.constraints.NotNull
import javax.ws.rs.*
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.Response


@Path("/user")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
class UserResource {
    private val userManager = UserManager()

    @GET
    @Secured
    fun get(@QueryParam("id") id: UUID): Response {

        return Response.ok(userManager.getUser(id)).build()
    }

    @PUT
    @Secured
    fun put(@NotNull user: User): Response {
        userManager.updateUser(user)
        return Response.ok(userManager.getUser(user.id)).build()
    }

    @POST
    @Secured
    fun post(@NotNull user: User): Response {
        val userUuid = userManager.addUser(user)
        return Response.ok(userUuid).build()
    }

    @DELETE
    fun delete(@NotNull @QueryParam("id") id: Int): Response {
        userManager.delete(id)
        return Response.ok().build()
    }

}
