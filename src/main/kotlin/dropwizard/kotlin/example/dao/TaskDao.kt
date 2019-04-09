package dropwizard.kotlin.example.dao

import dropwizard.kotlin.example.api.Task
import dropwizard.kotlin.example.api.User
import org.litote.kmongo.*


data class MyStateObject(val name: String, val age: Int)

class TaskDao {
    private val database = KMongo.createClient().getDatabase("test")
    private val col = database.getCollection<Task>()

    fun addTaskForUser(user: User, task: Task) {
        col.insertOne(Task(task.name, false, user))
    }

    fun updateToComplete(task: Task) {
        col.updateMany(Task::user eq task.user, set(Task::completed, true))
    }

    fun getTasksForUsers(user: User): Task?{
        return col.findOne(Task::user eq user)
    }

    fun getIncompleteItemsForUser(user: User): List<Task> {
        val items = mutableListOf<Task>()
        val mongoCursor = col.find().iterator()
        mongoCursor.use { cursor ->
            while (cursor.hasNext()) {
                items.add(cursor.next())
            }
        }
        return items.filter { it.user.email == user.email }.filter { !it.completed }
    }
}