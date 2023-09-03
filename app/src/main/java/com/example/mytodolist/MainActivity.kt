package com.example.mytodolist

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.adapter.TaskAdapter
import com.example.mytodolist.data.AppDatabase
import com.example.mytodolist.data.entity.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private var list = mutableListOf<Task>()
    private lateinit var adapter: TaskAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        fab = findViewById(R.id.fab)

        database = AppDatabase.getInstance(applicationContext)
        adapter = TaskAdapter(list)
        adapter.setDialog(object : TaskAdapter.Dialog {

            override fun onClick(position: Int) {
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle(list[position].taskname)
                dialog.setItems(
                    R.array.items_option,
                    DialogInterface.OnClickListener { dialog, which ->
                        if (which == 0) {
//                      coding ubah
                            val intent = Intent(this@MainActivity, EditorActivity::class.java)
                            intent.putExtra("id", list[position].tid)
                            startActivity(intent)
                        } else if (which == 1) {
//                        coding apus
                            database.taskDao().delete(list[position])
                            getData()
                        } else {
//                        coding batal
                            dialog.dismiss()
                        }
                    })
                val dialogView = dialog.create()
                dialogView.show()
            }

        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                RecyclerView.VERTICAL
            )
        )

        fab.setOnClickListener {
            startActivity(Intent(this, EditorActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getData() {
        list.clear()
        list.addAll(database.taskDao().getAll())
        adapter.notifyDataSetChanged()
    }
}