package com.example.todoapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("DELETE FROM task_table WHERE complete = 1")
    fun deleteAll()

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun edit(task: Task)

    @Delete
    suspend fun delete(task: Task)
}