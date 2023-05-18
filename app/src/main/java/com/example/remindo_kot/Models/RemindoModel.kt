package com.example.remindo_kot.Models

import android.content.Context
import com.example.remindo_kot.R
import com.google.android.material.card.MaterialCardView
import com.google.firebase.firestore.Exclude
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class RemindoModel() {
    var priority = 0
    var taskName: String? = null
    var taskDescription: String? = null
    var taskDuration: String? = null
        set(value) {
            field = value
            @Exclude
            endDate = try {
                parseMainFormat.parse(taskDuration)
            } catch (e: ParseException) {
                throw RuntimeException(e)
            }
        }
    var done: Boolean? = null
    var endDate: Date? = null
    @get:Exclude
    @set:Exclude
    var parseMainFormat = SimpleDateFormat("EEE dd MMM,yyyy hh:mm aa")

    constructor(
        priority: Int,
        taskName: String?,
        taskDescription: String?,
        taskDuration: String?,
        isDone: Boolean?
    ) : this() {
        this.priority = priority
        this.taskName = taskName
        this.taskDescription = taskDescription
        this.taskDuration = taskDuration
        done = isDone
        endDate = parseMainFormat.parse(taskDuration)

    }


    fun taskDate(): String {
        val dateFormat = SimpleDateFormat("EEE, dd MMM")
        return dateFormat.format(endDate)
    }

    fun taskTime(): String {
        val timeFormat = SimpleDateFormat("hh:mm aa")
        return timeFormat.format(endDate)
    }

    fun setStroke(cardView: MaterialCardView, context: Context) {
        if (done!!) {
            cardView.setStrokeColor(context.getColor(R.color.green))
        } else {
            when (priority) {
                1 -> cardView.setStrokeColor(context.getColor(R.color.red))
                2 -> cardView.setStrokeColor(context.getColor(R.color.orange))
                3 -> cardView.setStrokeColor(context.getColor(R.color.yellow))
            }
        }
    }

    fun remainingDuration(): String {
        val cal_date = Calendar.getInstance().time
        var different = endDate!!.time - cal_date.time
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        val elapsedDays = different / daysInMilli
        different = different % daysInMilli
        val elapsedHours = different / hoursInMilli
        different = different % hoursInMilli
        val elapsedMinutes = different / minutesInMilli
        different = different % minutesInMilli
        val elapsedSeconds = different / secondsInMilli

        if (elapsedDays == 0L) {
            return "$elapsedHours hrs $elapsedMinutes mins"
        } else if (elapsedHours == 0L) {
            return "$elapsedMinutes mins"
        }
        return "$elapsedDays days $elapsedHours hrs $elapsedMinutes mins"
    }
}