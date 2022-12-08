package com.example.todoapplication.ui.edit

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapplication.R
import com.example.todoapplication.data.Task
import com.example.todoapplication.databinding.FragmentEditTaskBinding
import com.example.todoapplication.viewmodel.TaskViewModel

class EditTaskFragment : Fragment() {

    private val viewModel:  TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val binding = FragmentEditTaskBinding.inflate(inflater)
        val args = EditTaskFragmentArgs.fromBundle(requireArguments())

        binding.apply {
            editTaskName.setText(args.task.name)
            description.editText?.setText(args.task.description)

            editButton.setOnClickListener {
                if(TextUtils.isEmpty(editTaskName.text)){
                    Toast.makeText(
                        requireContext(),
                        "Please type the task name",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                val task_name = editTaskName.text
                val description = description.editText?.text

                val task = Task(
                    args.task.id,
                    task_name.toString(),
                    description.toString()
                )
                viewModel.edit(task)
                Toast.makeText(
                    requireContext(),
                    "Task edited",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_editTaskFragment_to_taskFragment)
            }
        }
        return binding.root
    }

}