package com.example.mytodolist

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import com.example.mytodolist.data.AppDatabase
import com.example.mytodolist.data.entity.Task
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

class EditorActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var dateTimeTil: TextInputLayout
    private lateinit var dateTimeEt: EditText
    private lateinit var taskName: EditText
    private lateinit var prioritas: Spinner
    private lateinit var btnSave: Button
    private lateinit var database: AppDatabase

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        dateTimeEt = findViewById(R.id.date_time_et)
        dateTimeTil = findViewById(R.id.date_time_til)
        taskName = findViewById(R.id.task_name)
        prioritas = findViewById(R.id.spin)
        btnSave = findViewById(R.id.btn_simpan)

        database = AppDatabase.getInstance(applicationContext)

        dateTimeEt.setRawInputType(InputType.TYPE_NULL)
        dateTimeEt.isFocusable = false
        dateTimeEt.keyListener = null

        pickDate()

        val intent = intent.extras
        if (intent != null) {
            val id = intent.getInt("id", 0)
            val user = database.taskDao().get(id)

            taskName.setText(user.taskname)
            dateTimeEt.setText(user.datetime)
            prioritas.selectedItem.toString()
        }

        btnSave.setOnClickListener {
            if (taskName.text.isNotEmpty() && dateTimeEt.text.isNotEmpty()) {
                if (intent != null) {
                    database.taskDao().update(
                        Task(
                            intent.getInt("id", 0),
                            taskName.text.toString(),
                            dateTimeEt.text.toString(),
                            prioritas.selectedItem.toString()
                        )
                    )
                } else {
                    database.taskDao().insertAll(
                        Task(
                            null,
                            taskName.text.toString(),
                            dateTimeEt.text.toString(),
                            prioritas.selectedItem.toString()
                        )
                    )
                }

                finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    "silahkan isi semua data dengan valid",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)

    }

    private fun pickDate() {
        dateTimeEt.setOnClickListener {
            getDateTimeCalendar()

            DatePickerDialog(this, this, year, month, day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()
        TimePickerDialog(this, this, hour, minute, true).show()
    }


    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        dateTimeEt.setText("$savedDay-$savedMonth-$savedYear || $savedHour:$savedMinute")

    }
}