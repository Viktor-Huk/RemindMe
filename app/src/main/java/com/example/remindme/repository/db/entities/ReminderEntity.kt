package com.example.remindme.repository.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.remindme.models.Reminder
import java.util.Date

@Entity(tableName = "reminders_table")
data class ReminderEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "minutes")
    val minutes: String?,

    @ColumnInfo(name = "hours")
    val hours: String?,

    @ColumnInfo(name = "numberOfMonth")
    val numberOfMonth: String?,

    @ColumnInfo(name = "month")
    val month: String?,

    @ColumnInfo(name = "year")
    val year: String?,

    @ColumnInfo(name = "description")
    val description: String?
) {
    constructor(reminder: Reminder): this(
        reminder.id,
        reminder.title,
        reminder.minutes,
        reminder.hours,
        reminder.numberOfMonth,
        reminder.month,
        reminder.year,
        reminder.description
    )

    fun toReminder() = Reminder(id, title, minutes, hours, numberOfMonth, month, year, description)
}