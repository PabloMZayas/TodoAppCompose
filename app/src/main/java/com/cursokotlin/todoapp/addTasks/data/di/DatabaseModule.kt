package com.cursokotlin.todoapp.addTasks.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cursokotlin.todoapp.addTasks.data.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(context, TodoDatabase::class.java, "TaskDatabase").build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(database: TodoDatabase) = database.getTaskDao()
}