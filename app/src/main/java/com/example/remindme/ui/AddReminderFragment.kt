package com.example.remindme.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.remindme.App
import com.example.remindme.R
import com.example.remindme.models.Reminder
import com.example.remindme.ui.notifications.ReminderNotifications
import kotlinx.android.synthetic.main.fragment_edit_remind.*
import java.text.SimpleDateFormat
import java.util.*

class AddReminderFragment : Fragment() {

    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener
    private val calendar = Calendar.getInstance()
    private val notification = ReminderNotifications(App.INSTANCE)
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
    private val timeFormat: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.US)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_remind, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateEditText.setText(dateFormat.format(calendar.time))
        timeEditText.setText(timeFormat.format(calendar.time))

        dateEditText.setOnClickListener {
            val dialog = DatePickerDialog(
                requireActivity(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.setCancelable(false)
            dialog.datePicker.minDate = Calendar.getInstance().timeInMillis
            dialog.show()
        }

        timeEditText.setOnClickListener {
            val dialog = TimePickerDialog(
                requireActivity(),
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            dialog.setCancelable(false)
            dialog.show()
        }

        doneFab.setOnClickListener {
            if (titleEditText.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), R.string.toast_empty_title, Toast.LENGTH_SHORT)
                    .show()
            } else if (descriptionEditText.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), R.string.toast_empty_desc, Toast.LENGTH_SHORT)
                    .show()
            } else {
                val title = titleEditText.text.toString()
                val description = descriptionEditText.text.toString()
                val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
                val date = calendar.time

                viewModel.idAddedReminder.observe(this, androidx.lifecycle.Observer {
                    notification.setAlarm(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        it,
                        title,
                        description
                    )
                    requireActivity().supportFragmentManager.popBackStack()
                })

                viewModel.addReminder(
                    Reminder(
                        null,
                        title,
                        SimpleDateFormat("mm", Locale.US).format(date),
                        SimpleDateFormat("HH", Locale.US).format(date),
                        SimpleDateFormat("dd", Locale.US).format(date),
                        SimpleDateFormat("MM", Locale.US).format(date),
                        SimpleDateFormat("yy", Locale.US).format(date),
                        description
                    )
                )
            }
        }

        dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            dateEditText.setText(dateFormat.format(calendar.time))
        }

        timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            timeEditText.setText(timeFormat.format(calendar.time))
        }
    }
}
