package com.example.remindme.ui.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.remindme.R
import com.example.remindme.receivers.AlarmReceiver
import java.util.Calendar

class ReminderNotifications(private val context: Context) {

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nameChannel = context.getString(R.string.notification_channel_name)
            val descriptionChannel = context.getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(
                CHANNEL_ID,
                nameChannel,
                importance
            ).apply {
                description = descriptionChannel
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun setAlarm(
        year: Int,
        month: Int,
        dayOfMonth: Int,
        hour: Int,
        minute: Int,
        id: Int?,
        title: String,
        description: String
    ) {
        Log.i("tag", "set alarm ${id}")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(ID_REMINDER_IN_NOTIFICATION, id)
        intent.putExtra(TITLE_REMINDER_IN_NOTIFICATION, title)
        intent.putExtra(DESCRIPTION_REMINDER_IN_NOTIFICATION, description)


        val pendingIntent =
            id?.let {
                PendingIntent.getBroadcast(context,
                    it, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            }

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    companion object {
        const val CHANNEL_ID = "reminder_notification"
        const val VALUE_FROM_NOTIFICATION = "details_fragment"
        const val TITLE_REMINDER_IN_NOTIFICATION = "title"
        const val DESCRIPTION_REMINDER_IN_NOTIFICATION = "description"
        const val ID_REMINDER_IN_NOTIFICATION = "id"
    }
}