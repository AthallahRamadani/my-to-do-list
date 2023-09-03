package com.example.mytodolist.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) var tid: Int? = null,
    @ColumnInfo(name = "task_name") var taskname:  String?,
    @ColumnInfo(name = "date_time") var datetime:  String?,
    @ColumnInfo(name = "priotitas") var prioritas:  String?
)
