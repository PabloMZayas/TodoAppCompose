package com.cursokotlin.todoapp.addTasks.domain

import com.cursokotlin.todoapp.addTasks.data.TaskRepository
import com.cursokotlin.todoapp.addTasks.ui.models.TaskModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    operator fun invoke (): Flow<List<TaskModel>> = taskRepository.tasks
}