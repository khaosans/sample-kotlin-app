package dropwizard.kotlin.example.resources

import dropwizard.kotlin.example.api.Post
import dropwizard.kotlin.example.api.User
import dropwizard.kotlin.example.managers.PostManager
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.validation.constraints.NotNull
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/post")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class PostResource {
    private val userDb = ConcurrentHashMap<String, User>()

    private val postManager = PostManager()

    @GET
    fun get(@QueryParam("id") uuid: UUID): Post {
        val post = postManager.getPost(uuid)
        return post?: throw NotFoundException()
    }

    @PUT
    fun put(@NotNull post: Post): Response {

        postManager.updatePost(post)
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

