package com.cursokotlin.todoapp.addTasks.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.cursokotlin.todoapp.addTasks.ui.models.TaskModel

@Composable
fun TasksScreen(tasksViewModel: TasksViewModel) {
    val lifeCycle = LocalLifecycleOwner.current.lifecycle
    val showDialog by tasksViewModel.showDialog.observeAsState(initial = false)

    val uiState by produceState<TaskUiState>(
            initialValue = TaskUiState.Loading,
            key1 = lifeCycle,
            key2 = tasksViewModel) {
        lifeCycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            tasksViewModel.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is TaskUiState.Error -> {}
        is TaskUiState.Loading -> {}
        is TaskUiState.Success -> {
            Box(Modifier.fillMaxSize()) {
                AddTasksDialog(show = showDialog, onDismiss = { tasksViewModel.onCloseDialog() },
                        onTaskAdded = { tasksViewModel.onTaskCreated(it) })
                Fab(Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp), tasksViewModel)
                TaskList((uiState as TaskUiState.Success).tasks, tasksViewModel)
            }
        }
    }
}

@Composable
fun TaskList(myTasks: List<TaskModel>, tasksViewModel: TasksViewModel) {
    LazyColumn {
        items(myTasks, key = { it.id }) { task ->
            ItemTask(taskModel = task, tasksViewModel = tasksViewModel)
        }
    }
}

@Composable
fun ItemTask(taskModel: TaskModel, tasksViewModel: TasksViewModel) {
    Card(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    tasksViewModel.onRemoveTask(taskModel)
                })
            },
            border = BorderStroke(0.5.dp, Color.Green)) {
        Row(Modifier
                .fillMaxWidth()
                .background(Color.White),
                verticalAlignment = Alignment.CenterVertically) {
            Text(text = taskModel.task, modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp))
            Checkbox(checked = taskModel.selected,
                    onCheckedChange = { tasksViewModel.onTaskItemSelected(taskModel) })
        }
    }
}

@Composable
fun Fab(modifier: Modifier, tasksViewModel: TasksViewModel) {
    FloatingActionButton(modifier = modifier, onClick = {
        tasksViewModel.onOpenDialog()
    }) {
        Icon(Icons.Default.Add, contentDescription = "add tasks")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTasksDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String) -> Unit) {

    var myTask by remember { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(Modifier
                    .background(Color.White)
                    .padding(16.dp)) {
                Text(text = "Añade una tarea",
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(value = myTask, onValueChange = { myTask = it }, singleLine = true, maxLines = 1)
                Spacer(modifier = Modifier.size(16.dp))
                Button(onClick = {
                    onTaskAdded(myTask)
                    myTask = ""
                }, Modifier.fillMaxWidth()) {
                    Text(text = "Añadir tarea")
                }
            }
        }
    }
}
