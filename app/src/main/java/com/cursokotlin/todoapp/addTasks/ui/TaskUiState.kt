package com.cursokotlin.todoapp.addTasks.ui

import com.cursokotlin.todoapp.addTasks.ui.models.TaskModel

sealed interface TaskUiState {
    object Loading: TaskUiState
    data class Error(val throwable: Throwable): TaskUiState
    data class Success(val tasks: List<TaskModel>): TaskUiState
}
