package dropwizard.kotlin.example.dao

import dropwizard.kotlin.example.api.Task
import dropwizard.kotlin.example.api.User


fun main() {
    val user1 = User()
    val task1 = Task("task1", false, user1)
    val task2 = Task("task2", false, user1)

    val user2 = User(username = "username", email = "test@gmail.com", password = "random")
    val task3 = Task("task3", false, user2)
    val task4 = Task("task4", false, user2)
    val task5 = Task("task5", false, user2)

    val tasks = listOf(task1, task2, task3, task4, task5)

    val taskDAO = TaskDao()

    tasks.forEach {
        taskDAO.addTaskForUser(user1, it)
        taskDAO.addTaskForUser(user2, it)
    }

    val tasksForUser1 = taskDAO.getIncompleteItemsForUser(user1)

    tasksForUser1.forEach {
        taskDAO.updateToComplete(it)
    }

    val message = taskDAO.getIncompleteItemsForUser(user2)
    print(message)
}