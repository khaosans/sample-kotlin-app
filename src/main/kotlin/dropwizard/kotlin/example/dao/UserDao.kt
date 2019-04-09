package dropwizard.kotlin.example.dao

import dropwizard.kotlin.example.api.User
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper
import java.util.*

@RegisterMapper(UserMapper::class)
interface UserDao {

    @SqlUpdate("UPDATE users " +
            "SET  name=  :name, email = :email " +
            "WHERE id = :id;")
    fun updateUser(@Bind("id") id: UUID, @Bind("name") name: String, @Bind("email") email: String)

    @SqlUpdate("insert into users (id, name, email, password) values (:id, :name, :email, :password)")
    fun insert(@Bind("id") id: UUID, @Bind("name") name: String, @Bind("email") email: String, @Bind("password") password: String)

    @SqlQuery("select * from users where id = :id")
    fun findUserById(@Bind("id") id: UUID): User

    @SqlQuery("select * from users where email = :email limit 1")
    fun findUserByEmail(@Bind("email") email: String): User

    @SqlQuery("select count(*) from users")
    fun findCount(): Int

    @SqlUpdate("DELETE FROM users WHERE id = :id")
    fun delete(@Bind("id") id: Int?)

    /**
     * close with no args is used to close the connection
     */
    fun close()
}
