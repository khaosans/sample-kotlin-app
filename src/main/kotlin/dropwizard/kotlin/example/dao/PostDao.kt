package dropwizard.kotlin.example.dao

import dropwizard.kotlin.example.api.Post
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper
import java.util.*

@RegisterMapper(PostMapper::class)
interface PostDao {

    @SqlUpdate("UPDATE post " +
            "SET  post=  :post, user_uuid = :user_uuid " +
            "WHERE id = :id;")
    fun updatePost(@Bind("id") id: UUID, @Bind("post") post: String, @Bind("user_uuid") useruuid: UUID)

    @SqlUpdate("insert into post ( post, user_uuid) values (:post, :user_uuid)")

    fun insert(@Bind("id") id: UUID, @Bind("name") name: String, @Bind("email") email: String, @Bind("password") password: String)

    @SqlQuery("select * from post where id = :id")
    fun findPostById(@Bind("id") id: UUID): Post

    @SqlUpdate("DELETE FROM post WHERE id = :id")
    fun delete(@Bind("id") id: Int?)

    /**
     * close with no args is used to close the connection
     */
    fun close()
}
