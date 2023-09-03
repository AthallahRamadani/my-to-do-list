package com.example.mytodolist.adapter

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.R
import com.example.mytodolist.data.entity.Task

class TaskAdapter(var list: List<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    private lateinit var dialog: Dialog

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    interface Dialog {
        fun onClick(position: Int)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var taskname: TextView
        var datetime: TextView
        var prioritas: TextView

        init {
            taskname = view.findViewById(R.id.task_name)
            datetime = view.findViewById(R.id.waktu_tanggal)
            prioritas = view.findViewById(R.id.prioritas)
            view.setOnClickListener {
                dialog.onClick(layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_task, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.taskname.text = list[position].taskname
        holder.datetime.text = list[position].datetime
        holder.prioritas.text = list[position].prioritas
    }

}

