package com.cursokotlin.todoapp.addTasks.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cursokotlin.todoapp.addTasks.ui.models.TaskModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskEntity")
    fun getTasks(): Flow<List<TaskEntity>>

    @Insert
    suspend fun addTask(taskModel: TaskEntity)

    @Update
    suspend fun updateTask(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)
}
