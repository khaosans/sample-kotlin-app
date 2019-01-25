package dropwizard.kotlin.example

import dropwizard.kotlin.example.api.User
import dropwizard.kotlin.example.dao.UserDao
import org.skife.jdbi.v2.DBI
import java.util.*


class UserManager {

    private val dbi = DBI("jdbc:postgresql://localhost:5432/postgres", "main", "myPassword")

    private val db = dbi.open(UserDao::class.java)

    init {
        dbi.registerArgumentFactory(UUIDArgumentFactory())
    }

    fun getUser(id: UUID): User? {
        return db.findUserById(id).takeIf { it != null }
    }

    fun findUserByEmail(email: String): User {
        return db.findUserByEmail(email)
    }

    fun addUser(user: User): UUID {
        val randomUUID = UUID.randomUUID()
        db.insert(randomUUID, user.username, user.email, user.password)
        return randomUUID
    }


    fun updateUser(user: User) {

        val userId = db.findUserById(user.id).id

        return db.updateUser(userId, user.username, user.email)

    }

    fun getCount(): Int {
        return db.getCount()
    }

    fun delete(id: Int) {
        return db.delete(id)
    }
}