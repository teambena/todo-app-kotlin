package com.example.todoapplication.data

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "task_table")
data class Task (

    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val complete: Boolean = false
): Parcelable