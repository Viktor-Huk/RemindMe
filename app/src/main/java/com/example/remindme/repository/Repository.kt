package com.example.remindme.repository

import android.util.Log
import com.example.remindme.models.Reminder
import com.example.remindme.repository.db.DatabaseManager
import com.example.remindme.repository.db.entities.ReminderEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {

    private val reminderDao = DatabaseManager.getDatabase()?.reminderDao()

    suspend fun getReminders(): List<Reminder> {

        var reminders: List<Reminder> = listOf()

        withContext(Dispatchers.IO) {

            if (reminderDao != null) {
                if (!reminderDao.getAllReminders().isNullOrEmpty()) {
                    reminders = reminderDao.getAllReminders().map { it.toReminder() }
                }
            }
        }
        return reminders
    }

    suspend fun getReminderById(id: Int): Reminder? {

        return withContext(Dispatchers.IO) {
            reminderDao?.getReminderById(id)?.toReminder()
        }
    }

    suspend fun addReminder(reminder: Reminder): Int? {
        return withContext(Dispatchers.IO) {
           reminderDao?.addReminder(ReminderEntity(reminder))?.toInt()
        }
    }

    suspend fun deleteReminder(reminder: Reminder) {
        withContext(Dispatchers.IO) {
            reminderDao?.deleteReminder(ReminderEntity(reminder))
        }
    }

    suspend fun updateReminder(reminder: Reminder) {
        withContext(Dispatchers.IO) {
            reminderDao?.updateReminder(ReminderEntity(reminder))
        }
    }
}