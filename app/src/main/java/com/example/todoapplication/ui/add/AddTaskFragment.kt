package com.example.todoapplication.ui.add

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapplication.R
import com.example.todoapplication.data.Task
import com.example.todoapplication.databinding.FragmentAddTaskBinding
import com.example.todoapplication.viewmodel.TaskViewModel

class AddTaskFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentAddTaskBinding.inflate(inflater)

        binding.apply {
            addButton.setOnClickListener {
                if(TextUtils.isEmpty(taskName.text)){
                    Toast.makeText(
                        requireContext(),
                        "Please type the task name",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                val task_name = taskName.text.toString()
                val description = description.editText?.text.toString()

                val Task = Task(
                    getRandomString(),
                    task_name,
                    description
                )
                Toast.makeText(
                    requireContext(),
                    Task.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.insert(Task)
                Toast.makeText(
                    requireContext(),
                    "Task added",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_addTaskFragment_to_taskFragment)
            }
        }
        return binding.root
    }

    fun getRandomString() : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..20)
            .map { allowedChars.random() }
            .joinToString("")
    }

}