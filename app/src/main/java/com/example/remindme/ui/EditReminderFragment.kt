package com.example.remindme.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.remindme.R
import com.example.remindme.models.Reminder
import kotlinx.android.synthetic.main.fragment_edit_remind.*
import java.text.SimpleDateFormat
import java.util.*

class EditReminderFragment : Fragment() {

    private val calendar = Calendar.getInstance()
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var timeEditText: EditText
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

        titleEditText = view.findViewById(R.id.titleEditText)
        descriptionEditText = view.findViewById(R.id.descriptionEditText)
        dateEditText = view.findViewById(R.id.dateEditText)
        timeEditText = view.findViewById(R.id.timeEditText)

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val id = requireArguments().getInt("ID")

        viewModel.getReminderById(id)
        viewModel.reminderById.observe(this, androidx.lifecycle.Observer {
            updateFields(it)
        })

        dateEditText.setOnClickListener {
            val dialog = DatePickerDialog(
                requireActivity(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.setCancelable(false)
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
                viewModel.updateReminder(
                    Reminder(
                        id,
                        titleEditText.text.toString(),
                        calendar.get(Calendar.MINUTE).toString(),
                        calendar.get(Calendar.HOUR_OF_DAY).toString(),
                        calendar.get(Calendar.DAY_OF_MONTH).toString(),
                        (calendar.get(Calendar.MONTH) + 1).toString(),
                        calendar.get(Calendar.YEAR).toString(),
                        descriptionEditText.text.toString()
                    )
                )
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

    }

    private val dateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val picked = dateFormat.format(calendar.time)
            dateEditText.setText(picked)
        }

    private val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val picked = timeFormat.format(calendar.time)
        timeEditText.setText(picked)
    }

    private fun updateFields(reminder: Reminder) {
        titleEditText.setText(reminder.title)
        descriptionEditText.setText(reminder.description)
        dateEditText.setText("${reminder.numberOfMonth}.${reminder.month}.${reminder.year}")
        timeEditText.setText("${reminder.hours}:${reminder.minutes}")
    }
}