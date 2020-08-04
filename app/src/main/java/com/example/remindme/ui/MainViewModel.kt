package com.example.remindme.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindme.models.Reminder
import com.example.remindme.repository.Repository
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    private val repository = Repository()

    private val _reminders = MutableLiveData<List<Reminder>>()
    val reminders: LiveData<List<Reminder>> = _reminders

    private val _reminderById = MutableLiveData<Reminder>()
    val reminderById: LiveData<Reminder> = _reminderById

    private val _idAddedReminder = MutableLiveData<Int?>()
    val idAddedReminder: LiveData<Int?> = _idAddedReminder

    fun getReminders() {
        viewModelScope.launch {
            _reminders.value = repository.getReminders()
        }
    }

    fun getReminderById(id: Int) {
        viewModelScope.launch {
            _reminderById.value = repository.getReminderById(id)
        }
    }

    fun addReminder(reminder: Reminder) {
        viewModelScope.launch {
            _idAddedReminder.value = repository.addReminder(reminder)
        }
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            repository.deleteReminder(reminder)
        }
    }

    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch {
            repository.updateReminder(reminder)
        }
    }
}