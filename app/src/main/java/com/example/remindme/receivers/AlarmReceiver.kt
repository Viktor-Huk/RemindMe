package com.example.remindme.receivers

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.remindme.R
import com.example.remindme.ui.MainActivity
import com.example.remindme.ui.notifications.ReminderNotifications

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val id = intent?.extras?.get(ReminderNotifications.ID_REMINDER_IN_NOTIFICATION).toString()
        val title = intent?.extras?.get(ReminderNotifications.TITLE_REMINDER_IN_NOTIFICATION).toString()
        val description = intent?.extras?.get(ReminderNotifications.DESCRIPTION_REMINDER_IN_NOTIFICATION).toString()

        Log.i("tag", "receive ${id}")

        val detailsFragmentIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(ReminderNotifications.VALUE_FROM_NOTIFICATION, "1")
            putExtra(MainActivity.ID_FROM_NOTIFICATION, id)
        }
        //detailsFragmentIntent.putExtra(ReminderNotifications.VALUE_FROM_NOTIFICATION, "1")
        //detailsFragmentIntent.putExtra(ReminderNotifications.ID_REMINDER_IN_NOTIFICATION, id)

        val detailsFragmentPendingIntent = PendingIntent.getActivity(context, id.toInt(), detailsFragmentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notify = context?.let {
            NotificationCompat.Builder(it, ReminderNotifications.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_none)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(detailsFragmentPendingIntent)
                .setAutoCancel(true)
        }

        if (notify != null) {
            with(context.let { NotificationManagerCompat.from(it) }) {
                Log.i("tag", "id notification ${id}")
                this.notify(id.toInt(), notify.build())
            }
        }
    }
}