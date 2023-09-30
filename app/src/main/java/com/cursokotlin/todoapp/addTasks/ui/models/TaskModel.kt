package com.cursokotlin.todoapp.addTasks.ui.models

data class TaskModel (val id: Int = System.currentTimeMillis().hashCode(),
                      val task: String,
                      var selected: Boolean = false)
