package com.example.mytodolist.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mytodolist.data.entity.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Query("SELECT * FROM task WHERE tid IN (:taskIds)")
    fun loadAllByIds(taskIds: IntArray): List<Task>

    @Insert
    fun insertAll(vararg task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM task WHERE tid = :tid")
    fun get(tid: Int) : Task

    @Update
    fun update(task: Task)
}