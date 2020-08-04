package com.example.remindme.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.remindme.repository.db.daos.ReminderDao
import com.example.remindme.repository.db.entities.ReminderEntity

@Database(entities = [ReminderEntity::class], version = 1, exportSchema = false)
abstract class ReminderRoomDatabase: RoomDatabase() {

    abstract fun reminderDao(): ReminderDao
}