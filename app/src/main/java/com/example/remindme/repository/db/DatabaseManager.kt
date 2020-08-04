package com.example.remindme.repository.db

import androidx.room.Room
import com.example.remindme.App

object DatabaseManager {

    private var  INSTANCE: ReminderRoomDatabase? = null

    fun getDatabase(): ReminderRoomDatabase? {

        if (INSTANCE == null) {

            val instance = Room.databaseBuilder(
                App.INSTANCE,
                ReminderRoomDatabase::class.java,
                "reminders_database"
            ).build()

            INSTANCE = instance
        }

        return INSTANCE
    }
}