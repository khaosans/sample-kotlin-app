package dropwizard.kotlin.example.managers

import dropwizard.kotlin.example.api.User
import dropwizard.kotlin.example.dao.DBConnection
import dropwizard.kotlin.example.dao.UserDao
import java.util.*


interface IUserManager {
    fun getUser(id: UUID): User?
    fun findUserByEmail(email: String): User
    fun addUser(user: User): UUID
    fun updateUser(user: User)
    fun getCount(): Int
    fun delete(id: Int)
}

class UserManager : IUserManager {

    private val db = DBConnection().dbi.open(UserDao::class.java)

    override fun getUser(id: UUID): User? {
        return db.findUserById(id)
    }

    override fun findUserByEmail(email: String): User {
        return db.findUserByEmail(email)
    }

    override fun addUser(user: User): UUID {
        val randomUUID = UUID.randomUUID()
        db.insert(randomUUID, user.username, user.email, user.password)
        return randomUUID
    }


    override fun updateUser(user: User) {
        val userId = db.findUserById(user.id).id

        return db.updateUser(userId, user.username, user.email)
    }

    override fun getCount(): Int {
        return db.findCount()
    }

    override fun delete(id: Int) {
        return db.delete(id)
    }
}