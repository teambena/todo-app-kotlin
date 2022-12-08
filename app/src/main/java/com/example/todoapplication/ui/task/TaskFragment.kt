package com.example.todoapplication.ui.task

import android.app.AlertDialog
import android.graphics.Canvas
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.R
import com.example.todoapplication.data.Task
import com.example.todoapplication.databinding.FragmentTaskBinding
import com.example.todoapplication.viewmodel.TaskViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class TaskFragment : Fragment(), TaskAdapter.TaskClickListener  {

    private val viewModel: TaskViewModel by viewModels()
    private lateinit var adapter : TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentTaskBinding.inflate(inflater)

        binding.lifecycleOwner= this
        binding.viewModel = viewModel

        adapter = TaskAdapter(this)
        viewModel.getAllTasks.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        binding.apply {
            binding.recyclerView.adapter = adapter
            fabAddTask.setOnClickListener {
                findNavController().navigate(R.id.action_taskFragment_to_addTaskFragment)
            }
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback( 0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = adapter.currentList[position]
                if(direction == 4) {
                    AlertDialog.Builder(requireContext())
                        .setMessage("Are you sure you want to Delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes") { dialog, _ ->
                            viewModel.delete(task)
                            Toast.makeText(
                                requireContext(),
                                "Task deleted",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .setNegativeButton("No") { dialog, _ ->
                           adapter.notifyItemChanged(viewHolder.adapterPosition)
                            dialog.dismiss()
                        }.create().show()
                } else {
                    findNavController().navigate(TaskFragmentDirections.actionTaskFragmentToEditTaskFragment(task))
                }

            }

            override fun onChildDraw(
                c : Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ){
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(), R.color.deleteColor))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(requireContext(), R.color.editColor))
                    .create().decorate()


                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        }).attachToRecyclerView(binding.recyclerView)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCheckBoxClick(task: Task, isChecked: Boolean) {
        viewModel.onTaskCheckedChanged(task, isChecked)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_delete_all ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete All")
                    .setMessage("Do you want to delete all completed task?")
                    .setPositiveButton("Yes") { dialog, _ ->
                        viewModel.deleteAll()
                        dialog.dismiss()
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }.create().show()
        }
        return super.onOptionsItemSelected(item)
    }

}