package com.example.todoapplication.ui.task


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.data.Task
import com.example.todoapplication.databinding.RowLayoutBinding

class TaskAdapter(val clickListener: TaskClickListener) : ListAdapter<Task, TaskAdapter.ViewHolder>(TaskDiffCallback) {

    companion object TaskDiffCallback : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    }

    interface TaskClickListener {
        fun onCheckBoxClick(task: Task, isChecked: Boolean)
    }

    inner class ViewHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                checkBox.setOnClickListener {
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        clickListener.onCheckBoxClick(task, checkBox.isChecked)
                    }
                }
            }
        }

        fun bind(task: Task){
            binding.checkBox.isChecked = task.complete
            binding.checkBox.paint.isStrikeThruText = task.complete
            binding.task = task
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }
}



