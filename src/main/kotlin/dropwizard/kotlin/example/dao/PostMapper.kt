package dropwizard.kotlin.example.dao

import dropwizard.kotlin.example.api.Post
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper
import java.sql.ResultSet
import java.util.*

class PostMapper : ResultSetMapper<Post> {
    override fun map(index: Int, resultRow: ResultSet, ctx: StatementContext): Post {
        val uuid = resultRow.getObject(1, UUID::class.java)
        val userUuid = resultRow.getObject(3, UUID::class.java)
        return Post(uuid, resultRow.getString("post"), userUuid)
    }
}
