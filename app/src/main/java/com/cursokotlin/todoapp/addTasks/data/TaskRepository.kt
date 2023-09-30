package com.cursokotlin.todoapp.addTasks.data

import com.cursokotlin.todoapp.addTasks.ui.models.TaskModel
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    val tasks: Flow<List<TaskModel>> = taskDao.getTasks().map {
        item -> item.map { TaskModel(it.id, it.task, it.selected) }
    }

    suspend fun addTask(taskModel: TaskModel) {
        taskDao.addTask(TaskEntity(taskModel.id, taskModel.task, taskModel.selected))
    }

    suspend fun updateTask(taskModel: TaskModel) {
        taskDao.updateTask(TaskEntity(taskModel.id, taskModel.task, taskModel.selected))
    }

    suspend fun deleteTask(taskModel: TaskModel) {
        taskDao.deleteTask(TaskEntity(taskModel.id, taskModel.task, taskModel.selected))
    }
}