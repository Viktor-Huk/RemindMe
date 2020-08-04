package com.example.remindme.repository.db.daos

import androidx.room.*
import com.example.remindme.repository.db.entities.ReminderEntity

@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminders_table")
    suspend fun getAllReminders(): List<ReminderEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addReminder(reminder: ReminderEntity): Long?

    @Delete
    suspend fun deleteReminder(reminder: ReminderEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateReminder(reminder: ReminderEntity)

    @Query("SELECT * FROM reminders_table WHERE id LIKE :id")
    suspend fun getReminderById(id: Int): ReminderEntity
}