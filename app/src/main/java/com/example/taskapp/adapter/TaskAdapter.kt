package com.example.taskapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskapp.R
import com.example.taskapp.data.Status
import com.example.taskapp.data.Task
import com.example.taskapp.databinding.ItemTaskBinding

class TaskAdapter (
    private val context: Context,
     private val taskList: List <Task>,
     private val taskSelected: (Task, Int) -> Unit)
//)   : ListAdapter<Task, TaskAdapter.MyViewHolder>()
    : RecyclerView.Adapter<TaskAdapter.MyViewHolder>()
{

    companion object{
        val SELECT_BACK: Int = 1
        val SELECT_REMOVE: Int = 2
        val SELECT_EDIT: Int = 3
        val SELECT_DETAILS: Int = 4
        val SELECT_NEXT: Int = 5

//        private val
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return  MyViewHolder((ItemTaskBinding.inflate
            (LayoutInflater.from(parent.context))))
    }

    override fun getItemCount() = taskList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = taskList[position]

        holder.binding.textDescription.text = task.description

        setIndicators(task, holder)
    }

    private fun setIndicators(task: Task, holder: MyViewHolder) {
        when (task.status) {
            Status.TODO -> {

                holder.binding.ibBack.setColorFilter(
                    ContextCompat.getColor(
                        context, R.color.white))
                holder.binding.ibBack.isEnabled = false


                holder.binding.btnDelete.setOnClickListener{taskSelected(task, SELECT_REMOVE)}
                holder.binding.btnEdit.setOnClickListener{taskSelected(task, SELECT_EDIT)}
                holder.binding.btnDetails.setOnClickListener{taskSelected(task, SELECT_DETAILS)}
                holder.binding.ibNext.setOnClickListener{taskSelected(task, SELECT_NEXT)}

            }

            Status.DOING -> {
                holder.binding.ibBack.setColorFilter(
                    ContextCompat.getColor(
                        context, R.color.color_status_todo)
                )
                holder.binding.ibNext.setColorFilter(
                    ContextCompat.getColor(
                        context, R.color.color_status_done))

                holder.binding.btnDelete.setOnClickListener{taskSelected(task, SELECT_REMOVE)}
                holder.binding.btnEdit.setOnClickListener{taskSelected(task, SELECT_EDIT)}
                holder.binding.btnDetails.setOnClickListener{taskSelected(task, SELECT_DETAILS)}
                holder.binding.ibNext.setOnClickListener{taskSelected(task, SELECT_NEXT)}
                holder.binding.ibBack.setOnClickListener{taskSelected(task, SELECT_BACK)}
            }

            Status.DONE -> {
                holder.binding.ibNext.setColorFilter(
                    ContextCompat.getColor(
                        context, R.color.white))


                holder.binding.ibNext.isEnabled = false

                holder.binding.ibBack.setColorFilter(
                    ContextCompat.getColor(
                        context, R.color.color_status_todo)
                )

                holder.binding.btnDelete.setOnClickListener{taskSelected(task, SELECT_REMOVE)}
                holder.binding.btnEdit.setOnClickListener{taskSelected(task, SELECT_EDIT)}
                holder.binding.btnDetails.setOnClickListener{taskSelected(task, SELECT_DETAILS)}
                holder.binding.ibBack.setOnClickListener{taskSelected(task, SELECT_BACK)}
            }

        }
    }

 inner class MyViewHolder (val binding : ItemTaskBinding) :
     RecyclerView.ViewHolder (binding.root)
}