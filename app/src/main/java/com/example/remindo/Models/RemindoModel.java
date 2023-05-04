package com.example.remindo.Models;

import android.content.Context;

import com.example.remindo.R;
import com.google.android.material.card.MaterialCardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RemindoModel {
    int priority;
    String taskName;
    String taskDescription;
    String taskDuration;
    Boolean isDone;

    Date endDate;
    SimpleDateFormat parseMainFormat = new SimpleDateFormat("EEE dd MMM,yyyy hh:mm aa");


    public RemindoModel() {
    }

    public RemindoModel(int priority, String taskName, String taskDescription, String taskDuration, Boolean isDone) {
        this.priority = priority;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDuration = taskDuration;
        this.isDone = isDone;
        try {
            endDate = parseMainFormat.parse(taskDuration);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDuration() {
        return taskDuration;
    }

    public void setTaskDuration(String taskDuration) {
        this.taskDuration = taskDuration;
        try {
            endDate = parseMainFormat.parse(taskDuration);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String taskDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM");
        return dateFormat.format(endDate);
    }


    public String taskTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        return timeFormat.format(endDate);
    }


    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public void setStroke(MaterialCardView cardView, Context context){
        if(isDone) {
            cardView.setStrokeColor(context.getColor(R.color.green));
        }
        else {
            switch (this.getPriority()){
                case 1:
                    cardView.setStrokeColor(context.getColor(R.color.red));
                    break;
                case 2:
                    cardView.setStrokeColor(context.getColor(R.color.orange));
                    break;
                case 3:
                    cardView.setStrokeColor(context.getColor(R.color.yellow));
                    break;
            }
        }
    }

    public String remainingDuration(){
        Date cal_date = Calendar.getInstance().getTime();
        long different = endDate.getTime() - cal_date.getTime();


        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

//        System.out.printf(
//                "%d days, %d hours, %d minutes, %d seconds%n",
//                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        if(elapsedDays==0){
            return elapsedHours + " hrs " + elapsedMinutes + " mins";
        } else if (elapsedHours==0) {
            return elapsedMinutes + " mins";
        }

        return elapsedDays + " days " + elapsedHours + " hrs " + elapsedMinutes + " mins";

    }

}
