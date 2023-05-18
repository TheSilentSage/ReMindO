package com.example.remindo_kot.Activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.remindo_kot.Database.RemindoFireBase
import com.example.remindo_kot.Models.RemindoModel
import com.example.remindo_kot.R
import com.google.android.material.chip.Chip
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class Add_New_Task : AppCompatActivity() {

    var priority = 3
    lateinit var remindoModel: RemindoModel
    lateinit var remindoFireBase: RemindoFireBase
    var datetime = ""
    private var mYear = 0
    private  var mMonth = 0
    private  var mDay = 0
    private  var mHour = 0
    private  var mMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_task_activity)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        remindoFireBase = RemindoFireBase()
        val taskNameTextView = findViewById<EditText>(R.id.taskNameTextView)
        val taskDescriptionTextView = findViewById<EditText>(R.id.taskDescriptionTextView)
        val taskDurationButton = findViewById<ImageButton>(R.id.taskDurationButton)
        val saveToDoButton = findViewById<Button>(R.id.saveNewTaskButton)
        val dateTextView = findViewById<TextView>(R.id.dateTextView)
        val chipHigh = findViewById<Chip>(R.id.chipHigh)
        val chipMid = findViewById<Chip>(R.id.chipMid)
        val chipLow = findViewById<Chip>(R.id.chipLow)
        chipLow.isChecked = true

        chipHigh.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                priority = 1
                chipMid.isChecked = false
                chipLow.isChecked = false
            }
        }

        chipMid.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                priority = 2
                chipHigh.isChecked = false
                chipLow.isChecked = false
            }
        }

        chipLow.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                priority = 3
                chipHigh.isChecked = false
                chipMid.isChecked = false
            }
        }


        taskDurationButton.setOnClickListener {
            // Get Current Date

            val c = Calendar.getInstance()
            mYear = c[Calendar.YEAR]
            mMonth = c[Calendar.MONTH]
            mDay = c[Calendar.DAY_OF_MONTH]


            val datePickerDialog = DatePickerDialog(
                this@Add_New_Task,
                { view, year, monthOfYear, dayOfMonth ->
                    datetime += dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
                    // Get Current Time
                    val c = Calendar.getInstance()
                    mHour = c[Calendar.HOUR_OF_DAY]
                    mMinute = c[Calendar.MINUTE]

                    // Launch Time Picker Dialog
                    val timePickerDialog = TimePickerDialog(
                        this@Add_New_Task,
                        { view, hourOfDay, minute ->
                            datetime += " $hourOfDay:$minute"
                            val simpleDateFormat = SimpleDateFormat("dd/M/yyyy hh:mm")
                            val displayFormat = SimpleDateFormat("EEE dd MMM,yyyy   hh:mm aa")
                            val day: Date
                            day = try {
                                simpleDateFormat.parse(datetime)
                            } catch (e: ParseException) {
                                throw RuntimeException(e)
                            }
                            datetime = displayFormat.format(day)
                            dateTextView.text = datetime
                        }, mHour, mMinute, false
                    )
                    timePickerDialog.show()
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }
        saveToDoButton.setOnClickListener {
            val taskName = taskNameTextView.text.toString()
            val taskDescription = taskDescriptionTextView.text.toString()
            remindoModel = RemindoModel(priority, taskName, taskDescription, datetime, false)


            remindoFireBase.addToFireBase(remindoModel,this@Add_New_Task)
            val homeActivity = Intent(this@Add_New_Task, Remindo_Home::class.java)
            startActivity(homeActivity)
            finish()

        }

    }
}
