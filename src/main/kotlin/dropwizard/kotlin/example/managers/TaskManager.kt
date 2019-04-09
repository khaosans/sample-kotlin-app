package dropwizard.kotlin.example.managers

import dropwizard.kotlin.example.api.Task
import dropwizard.kotlin.example.api.User
import dropwizard.kotlin.example.dao.DBConnection
import dropwizard.kotlin.example.dao.TaskDao

class TaskManager  {
    private val dbi = DBConnection().dbi.open(TaskDao::class.java)!!

    fun getAllTasks(user: User): Task {
        return Task("Do this", false, user)
    }
}