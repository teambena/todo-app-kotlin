package com.example.todoapplication.repository

import androidx.lifecycle.LiveData
import com.example.todoapplication.data.Task
import com.example.todoapplication.data.TaskDao
import kotlinx.coroutines.Dispatchers


class TaskRepository(val taskDao: TaskDao) {

    fun getAllTasks() : LiveData<List<Task>> = taskDao.getAllTasks()

    suspend fun insert(task: Task) = taskDao.insert(task)
    suspend fun edit(task: Task) = taskDao.edit(task)
    suspend fun delete(task: Task) = taskDao.delete(task)
    fun deleteAll() = taskDao.deleteAll()
    suspend fun onTaskCheckedChanged(task: Task, isChecked: Boolean) = taskDao.edit(task.copy(complete = isChecked))

}