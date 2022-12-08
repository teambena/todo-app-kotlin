package com.example.todoapplication.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.todoapplication.data.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirebaseRepository(val taskRepository: TaskRepository){

    val db = Firebase.firestore
    val collection = db.collection("task_table")

    fun insert(task: Task) {
        collection
            .add(task)
            .addOnSuccessListener { document ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${document.id}")
                CoroutineScope(Dispatchers.IO).launch {
                    taskRepository.insert(task)
                }
            }
            .addOnFailureListener { error ->
                Log.w(TAG, "Error adding document", error)
            }
    }

    fun edit(task: Task) {
        collection
            .whereEqualTo("id", task.id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    collection
                        .document(document.id)
                        .update("name", task.name,
                        "description", task.description)
                    Log.d(TAG, "${document.id} => ${document.data}")

                }
                CoroutineScope(Dispatchers.IO).launch {
                    taskRepository.edit(task)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun delete(task: Task) {
        collection
            .whereEqualTo("id", task.id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    collection
                        .document(document.id)
                        .delete()
                }
                CoroutineScope(Dispatchers.IO).launch {
                    taskRepository.delete(task)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun deleteAll(){
        collection
            .whereEqualTo("complete", true)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    collection
                        .document(document.id)
                        .delete()
                }
                CoroutineScope(Dispatchers.IO).launch {
                    taskRepository.deleteAll()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun onTaskCheckedChanged(task: Task, isChecked: Boolean) {
        collection
            .whereEqualTo("id", task.id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if(isChecked){
                        collection
                            .document(document.id)
                            .update("complete", true)
                    }
                    else {
                        collection
                            .document(document.id)
                            .update("complete", false)
                    }
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                CoroutineScope(Dispatchers.IO).launch {
                    taskRepository.onTaskCheckedChanged(task, isChecked)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
}