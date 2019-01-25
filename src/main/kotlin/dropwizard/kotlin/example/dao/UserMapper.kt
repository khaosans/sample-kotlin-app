package dropwizard.kotlin.example.dao

import dropwizard.kotlin.example.api.User
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper
import java.sql.ResultSet
import java.util.*


class UserMapper : ResultSetMapper<User> {

    override fun map(index: Int, resultRow: ResultSet, ctx: StatementContext): User {

        val uuid = resultRow.getObject(1, UUID::class.java)
        return User(uuid, resultRow.getString("name"), resultRow.getString("email"), resultRow.getString("password"))
    }

}