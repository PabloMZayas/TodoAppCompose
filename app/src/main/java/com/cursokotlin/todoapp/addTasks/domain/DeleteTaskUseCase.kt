package com.cursokotlin.todoapp.addTasks.domain

import com.cursokotlin.todoapp.addTasks.data.TaskRepository
import com.cursokotlin.todoapp.addTasks.ui.models.TaskModel
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    suspend operator fun invoke(taskModel: TaskModel) {
        taskRepository.deleteTask(taskModel)
    }
}