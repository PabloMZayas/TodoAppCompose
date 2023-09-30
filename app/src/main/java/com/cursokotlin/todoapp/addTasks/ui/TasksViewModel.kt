package com.cursokotlin.todoapp.addTasks.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.todoapp.addTasks.domain.AddTaskUseCase
import com.cursokotlin.todoapp.addTasks.domain.DeleteTaskUseCase
import com.cursokotlin.todoapp.addTasks.domain.GetTasksUseCase
import com.cursokotlin.todoapp.addTasks.domain.UpdateTaskUseCase
import com.cursokotlin.todoapp.addTasks.ui.TaskUiState.Success
import com.cursokotlin.todoapp.addTasks.ui.models.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class TasksViewModel @Inject constructor(
        getTasksUseCase: GetTasksUseCase,
        private val updateTaskUseCase: UpdateTaskUseCase,
        private val addTaskUseCase: AddTaskUseCase,
        private val deleteTaskUseCase: DeleteTaskUseCase
): ViewModel() {

    val uiState: StateFlow<TaskUiState> = getTasksUseCase().map (::Success)
            .catch { TaskUiState.Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskUiState.Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    fun onCloseDialog() {
        _showDialog.value = false
    }

    fun onOpenDialog() {
        _showDialog.value = true
    }

    fun onTaskCreated(task: String) {
        //_tasks.add(TaskModel(task = task))
        _showDialog.value = false
        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = task))
        }
    }

    fun onTaskItemSelected(taskModel: TaskModel) {
       viewModelScope.launch {
           updateTaskUseCase(taskModel.copy(selected = !taskModel.selected))
       }
    }

    fun onRemoveTask(taskModel: TaskModel) {
       viewModelScope.launch {
           deleteTaskUseCase(taskModel)
       }
    }
}