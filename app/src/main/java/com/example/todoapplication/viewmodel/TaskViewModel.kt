package com.example.todoapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.data.Task
import com.example.todoapplication.data.TaskDatabase
import com.example.todoapplication.repository.FirebaseRepository
import com.example.todoapplication.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application){

    private val taskDao = TaskDatabase.getDatabase(application).taskDao()
    private val repository: TaskRepository = TaskRepository(taskDao)
    private val firebaseRepository: FirebaseRepository = FirebaseRepository(repository)


    val getAllTasks: LiveData<List<Task>> = repository.getAllTasks()

    fun insert(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.insert(task)
        }
    }

    fun edit(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.edit(task)
        }
    }

    fun delete(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.delete(task)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.deleteAll()
        }
    }

    fun onTaskCheckedChanged(task: Task, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseRepository.onTaskCheckedChanged(task, isChecked)
        }
    }


}