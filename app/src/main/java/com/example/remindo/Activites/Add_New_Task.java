package com.example.remindo.Activites;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.remindo.R;
import com.example.remindo.Models.RemindoModel;
import com.example.remindo.database.RemindoFireBase;
import com.google.android.material.chip.Chip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Add_New_Task extends AppCompatActivity {
    int priority = 3;
    RemindoModel remindoModel;
    RemindoFireBase remindoFireBase;
    String datetime = "";
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task_activity);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

//        remindoFireBase = getIntent().getParcelableExtra("db");
            remindoFireBase = new RemindoFireBase();
        EditText taskNameTextView = findViewById(R.id.taskNameTextView);
        EditText taskDescriptionTextView = findViewById(R.id.taskDescriptionTextView);
        ImageButton taskDurationButton = findViewById(R.id.taskDurationButton);
        Button saveToDoButton = findViewById(R.id.saveNewTaskButton);
        TextView dateTextView = findViewById(R.id.dateTextView);


        Chip chipHigh = findViewById(R.id.chipHigh);
        Chip chipMid = findViewById(R.id.chipMid);
        Chip chipLow = findViewById(R.id.chipLow);

        chipLow.setChecked(true);

        chipHigh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    priority = 1;
                    chipMid.setChecked(false);
                    chipLow.setChecked(false);
                }
            }
        });

        chipMid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    priority = 2;
                    chipHigh.setChecked(false);
                    chipLow.setChecked(false);
                }
            }
        });

        chipLow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    priority = 3;
                    chipHigh.setChecked(false);
                    chipMid.setChecked(false);
                }
            }
        });

        taskDurationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Add_New_Task.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                datetime += dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                // Get Current Time
                                final Calendar c = Calendar.getInstance();
                                mHour = c.get(Calendar.HOUR_OF_DAY);
                                mMinute = c.get(Calendar.MINUTE);

                                // Launch Time Picker Dialog
                                TimePickerDialog timePickerDialog = new TimePickerDialog(Add_New_Task.this,
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                                  int minute) {
                                                datetime += " " + hourOfDay + ":" + minute;
                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm");
                                                SimpleDateFormat displayFormat = new SimpleDateFormat("EEE dd MMM,yyyy   hh:mm aa");

                                                Date day;
                                                try {
                                                    day = simpleDateFormat.parse(datetime);
                                                } catch (ParseException e) {
                                                    throw new RuntimeException(e);
                                                }
                                                datetime = displayFormat.format(day);
                                                dateTextView.setText(datetime);
                                            }
                                        }, mHour, mMinute, false);
                                timePickerDialog.show();

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();




            }



        });

        saveToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = taskNameTextView.getText().toString();
                String taskDescription = taskDescriptionTextView.getText().toString();

               remindoModel = new RemindoModel(priority,taskName,taskDescription,datetime,false);
//                remindoViewModel = new RemindoViewModel(1,"Buy Gold","10 rs Milk packet short one","Wed 03 May,2023 11:22 AM",false);


                remindoFireBase.addToFireBase(remindoModel);
                Intent homeActivity = new Intent(Add_New_Task.this,Remindo_Home.class);
                startActivity(homeActivity);
                finish();
            }
        });

    }
}
